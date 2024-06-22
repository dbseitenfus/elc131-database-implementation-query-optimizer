package ibd.table.prototype.column;

import ibd.table.prototype.metadata.Metadata;

public class StringColumn extends Column{
    
    public StringColumn(String name,int size,short flags) {
        super(name, size, flags);
    }
    
    public StringColumn(String name, short size) {
        super(name, size, (short) (Metadata.STRING|Metadata.DINAMIC_COLUMN_SIZE));
    }
    public StringColumn(String name){
        this(name, (short) 255);
    }
    
    @Override
    public String getType() {
        return Column.STRING_TYPE;
    }
}
