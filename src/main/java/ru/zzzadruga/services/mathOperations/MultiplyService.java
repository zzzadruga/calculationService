package ru.zzzadruga.services.mathOperations;

import java.math.BigDecimal;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;
import ru.zzzadruga.services.mathOperations.common.MathOperationService;

public class MultiplyService implements MathOperationService {
    @IgniteInstanceResource
    private Ignite ignite;
    public static final String SERVICE_NAME = "MultiplyService";
    private IgniteAtomicSequence sequence;

    @Override
    public BigDecimal calculate(BigDecimal operand1, BigDecimal operand2) {
        sequence.getAndIncrement();
        return operand1.multiply(operand2);
    }

    @Override
    public void cancel(ServiceContext ctx) {

    }

    @Override
    public void init(ServiceContext ctx) throws Exception {

    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
        sequence = ignite.atomicSequence("multiplyCounter", 0, true);
    }

    @Override
    public long getCount() {
        return sequence.get();
    }
}
