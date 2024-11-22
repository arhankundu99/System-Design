package models;

import exceptions.SameDataTypeConversionException;

import java.util.HashMap;
import java.util.Map;

public class DataTransformerFactory {
    Map<DataType, Map<DataType, IDataTransformer>> dataTransformerMap;
    public DataTransformerFactory() {
        dataTransformerMap = new HashMap<>();
        dataTransformerMap.put(DataType.CSV, new HashMap<>());
        dataTransformerMap.put(DataType.JSON, new HashMap<>());
        dataTransformerMap.put(DataType.XML, new HashMap<>());


        dataTransformerMap.get(DataType.CSV).put(DataType.JSON, new CSVToJSONTransformer());
        dataTransformerMap.get(DataType.CSV).put(DataType.XML, new CSVToXMLTransformer());
        dataTransformerMap.get(DataType.JSON).put(DataType.XML, new JSONToXMLTransformer());
    }

    public IDataTransformer getDataTransformer(DataType sourceDataType, DataType targetDataType) throws SameDataTypeConversionException {
        if (sourceDataType == targetDataType) {
            throw new SameDataTypeConversionException("The source and the target are of same datatype.");
        }

        return dataTransformerMap.get(sourceDataType).get(targetDataType);
    }
}
