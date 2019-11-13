package diaballik.resource.adapters;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntStringAdapter extends XmlAdapter<String, Integer> {


    @Override
    public Integer unmarshal(final String v) throws Exception {
        return DatatypeConverter.parseInt(v);
    }

    @Override
    public String marshal(final Integer v) throws Exception {
        return DatatypeConverter.printInt(v);
    }
}
