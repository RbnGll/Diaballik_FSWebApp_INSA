package diaballik.resource.adapters;

import diaballik.model.player.Piece;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Optional;

public class OptionalPieceAdapter extends XmlAdapter<Piece, Optional<Piece>> {

    @Override
    public Optional<Piece> unmarshal(final Piece v) throws Exception {
        return Optional.ofNullable(v);
    }

    @Override
    public Piece marshal(final Optional<Piece> v) throws Exception {
        if (v.isEmpty()) {
            return null;
        } else {
            return v.get();
        }
    }

}
