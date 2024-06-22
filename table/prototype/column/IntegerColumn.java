package ibd.table.prototype.column;

import ibd.table.prototype.metadata.Metadata;

public class IntegerColumn extends Column{
    
    public IntegerColumn(String name,int size,short flags) {
        super(name, size, flags);
    }
    
    public IntegerColumn(String name,boolean primaryKey,boolean nullable) {
        super(name, (short) 4, (primaryKey ? Metadata.PRIMARY_KEY:(nullable?Metadata.CAN_NULL_COLUMN:Metadata.NONE)));
    }
    public IntegerColumn(String name,boolean primaryKey) {
        this(name,primaryKey,false);
    }
    public IntegerColumn(String name) {
        this(name,false);
    }
    
    @Override
    public String getType() {
        return Column.INTEGER_TYPE;
    }
}
