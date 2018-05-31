package ru.zzzadruga.services.calculation;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import ru.zzzadruga.services.calculation.common.CalculationService;
import ru.zzzadruga.services.calculation.common.MathExpression;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculationServiceImpl implements CalculationService {
    @IgniteInstanceResource
    private Ignite ignite;

    private IgniteCache<Long, MathExpression> stagingArea;

    private IgniteAtomicSequence sequence;

    @Override
    public void cancel(ServiceContext ctx) {
        System.out.println("Stopping Calculation Service on node:" + ignite.cluster().localNode());
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        System.out.println("Initializing Calculation Service on node:" + ignite.cluster().localNode());
        stagingArea = ignite.cache("staging-area");
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        System.out.println("Executing Calculation Service on node:" + ignite.cluster().localNode());
        sequence = ignite.atomicSequence("mathExpressionID", 0, true);
    }

    @Override
    public String getResult(String mathExpression) {
        long mathExpressionID = sequence.getAndIncrement();
        MathExpression expression = new MathExpression(mathExpression, LocalDateTime.now());
        stagingArea.put(mathExpressionID, expression);
        try{
            expression.setResult(ReversePolishNotation.calculateExpression(mathExpression));
            expression.setValid(true);
            expression.setDecisionTime(LocalDateTime.now());
            stagingArea.put(mathExpressionID, expression);
            return "Result: " + expression.getResult();
        } catch (Exception e){
            return e.getMessage();
        }

    }

    @Override
    public List<String> getHistory() {
        Set<Long> allKeys = new HashSet<>();
        for (int i = 1; i <= sequence.get(); i++) {
            allKeys.add((long)i);
        }
        return stagingArea.getAll(allKeys).entrySet().stream().map(entry -> entry.getValue().toString()).collect(Collectors.toList());
    }

    @Override
    public String getStatistics() {
        return "Total expressions: " + sequence.get() + "\n";
    }
}
