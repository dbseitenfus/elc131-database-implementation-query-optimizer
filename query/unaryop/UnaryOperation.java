/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query.unaryop;

import ibd.query.Operation;
import ibd.query.ReferedDataSource;

/**
 * An unary operation accesses data that comes from a child operation to perform
 * data transformation.
 *
 * @author Sergio
 */
public abstract class UnaryOperation extends Operation {

    /**
     * the child operation.
     */
    protected Operation childOperation;

    /**
     *
     * @param childOperation the child operation
     * @throws Exception
     */
    public UnaryOperation(Operation childOperation) throws Exception {
        setChildOperation(childOperation);
    }

    /**
     * sets the child operation
     *
     * @param childOperation the child operation
     */
    public final void setChildOperation(Operation childOperation) {
        this.childOperation = childOperation;
        childOperation.setParentOperation(this);
    }

    /**
     *
     * @return the child operation
     */
    public Operation getChildOperation() {
        return childOperation;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();
        //the preparation is propagated to the child operation
        childOperation.prepare();

    }

    @Override
    public void close() throws Exception {
        childOperation.close();
    }

    /**
     * {@inheritDoc }
     * copies the aliases from the child operation to this unary operation
     *
     * @throws Exception
     */
    @Override
    public void setDataSourcesInfo() throws Exception {
        childOperation.setDataSourcesInfo();
        ReferedDataSource s[] = childOperation.getDataSources();
        dataSources = new ReferedDataSource[s.length];
        System.arraycopy(s, 0, dataSources, 0, s.length);

    }

    @Override
    public final void setProcessedOperations() {
        super.setProcessedOperations();
        childOperation.setProcessedOperations();
    }

}
