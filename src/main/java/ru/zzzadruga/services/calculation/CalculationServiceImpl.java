package ru.zzzadruga.services.calculation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteSet;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceConfiguration;
import org.apache.ignite.services.ServiceContext;
import ru.zzzadruga.common.CommonConfigs;
import ru.zzzadruga.services.calculation.common.CalculationService;
import ru.zzzadruga.services.calculation.common.MathExpression;
import ru.zzzadruga.services.mathOperations.AddService;
import ru.zzzadruga.services.mathOperations.DivideService;
import ru.zzzadruga.services.mathOperations.MultiplyService;
import ru.zzzadruga.services.mathOperations.SubtractService;
import ru.zzzadruga.services.mathOperations.common.MathOperationService;

public class CalculationServiceImpl implements CalculationService {
    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<Long, MathExpression> stagingArea;

    private IgniteSet<Long> keys;

    private IgniteAtomicSequence sequence;

    Map<String, MathOperationService> operations = new HashMap<>();

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping Calculation Service on node:" + ignite.cluster().localNode());
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Initializing Calculation Service on node:" + ignite.cluster().localNode());
        stagingArea = ignite.cache("math.expressions");
        keys = ignite.set("keys", new CollectionConfiguration().setGroupName("keys.group"));
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing Calculation Service on node:" + ignite.cluster().localNode());
        sequence = ignite.atomicSequence("mathExpressionID", 0, true);
        for (Map.Entry<String, ServiceConfiguration> entry : CommonConfigs.MATH_SERVICES_CONFIG().entrySet()) {
            operations.put(entry.getKey(),
                ignite.services().serviceProxy(entry.getKey(), MathOperationService.class, false));
        }
    }

    @Override
    public String getResult(String mathExpression) {
        long mathExpressionID = sequence.getAndIncrement();
        MathExpression expression = new MathExpression(mathExpression, LocalDateTime.now());
        stagingArea.put(mathExpressionID, expression);
        keys.add(mathExpressionID);
        try {
            expression.setResult(ReversePolishNotation.calculateExpression(mathExpression, operations));
            expression.setValid(true);
            expression.setDecisionTime(LocalDateTime.now());
            stagingArea.put(mathExpressionID, expression);
            return "Result: " + expression.getResult();
        }
        catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    public List<String> getHistory() {
        return stagingArea.getAll(keys).entrySet().stream()
            .map(entry -> entry.getValue().toString()).collect(Collectors.toList());
    }

    @Override
    public String getStatistics() {
        return "Total expressions: " + sequence.get() + "\n" +
            "Total operations: " + operations.entrySet().stream().mapToLong(v -> v.getValue().getCount()).sum() + "\n" +
            "   Add (+): " + operations.get(AddService.SERVICE_NAME).getCount() + "\n" +
            "   Subtract (-): " + operations.get(SubtractService.SERVICE_NAME).getCount() + "\n" +
            "   Divide (÷): " + operations.get(DivideService.SERVICE_NAME).getCount() + "\n" +
            "   Multiply (×): " + operations.get(MultiplyService.SERVICE_NAME).getCount();
    }
}
