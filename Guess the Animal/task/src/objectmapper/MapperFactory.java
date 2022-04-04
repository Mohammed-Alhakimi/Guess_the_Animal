package objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


public class MapperFactory {
    public ObjectMapper createObjectMapper(String type)
    {
        if (type == null || type.isEmpty())
            return null;
        switch (type) {
            case "json":
                return new JsonMapper();
            case "xml":
                return new XmlMapper();
            case "yaml":
                return new YAMLMapper();
            default:
                throw new IllegalArgumentException("Unknown type "+type);
        }
    }
}
