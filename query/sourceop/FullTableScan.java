/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.query.sourceop;

import ibd.query.UnpagedOperationIterator;
import ibd.query.Tuple;
import ibd.table.Table;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import ibd.table.prototype.LinkedDataRow;
import java.util.List;

/**
 * scans all records stored in a table
 *
 * @author Sergio
 */
public class FullTableScan extends SourceOperation {

    /**
     * the table reached from this operation
     */
    public Table table;

    /**
     *
     * @param tableAlias the alias of the table reached by this operation
     * @param table the table reached from this operation
     */
    public FullTableScan(String tableAlias, Table table) {
        super(tableAlias);
        this.table = table;
    }

    @Override
    public void setDataSourcesInfo() throws Exception {
        super.setDataSourcesInfo();
        dataSources[0].prototype = table.getPrototype();
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public String toString() {
        return "[" + dataSourceAlias + "] Table Scan";
    }

    /**
     * {@inheritDoc }
     *
     * @return an iterator that performs a full table scan
     */
    @Override
    public Iterator<Tuple> lookUp_(List<Tuple> processedTuples, boolean withFilterDelegation) {
        return new FullTableScanIterator(processedTuples, withFilterDelegation);
    }

    /**
     * the class that produces resulting tuples from a full table scan
     */
    public class FullTableScanIterator extends UnpagedOperationIterator {

        //the iterator over the child operation
        Iterator<LinkedDataRow> iterator;

        public FullTableScanIterator(List<Tuple> processedTuples, boolean withFilterDelegation) {
            super(processedTuples, withFilterDelegation, getDelegatedFilters());

            try {
                //this iterator provides access to all stored rows
                iterator = table.getAllRecordsIterator();
            } catch (Exception ex) {
                Logger.getLogger(FullTableScan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        protected Tuple findNextTuple() {

            while (iterator.hasNext()) {
                LinkedDataRow row = iterator.next();
                Tuple tuple = new Tuple();
                //the resulting tuple contains a single row taken from the table
                tuple.setSingleSourceRow(dataSources[0].alias, row);
                //a tuple must satisfy the lookup filter that comes from the parent operation
                if (lookup.match(tuple)) {
                    return tuple;
                }
            }

            return null;
        }

    }
}
