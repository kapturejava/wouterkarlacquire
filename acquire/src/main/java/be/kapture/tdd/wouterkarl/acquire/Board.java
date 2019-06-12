package be.kapture.tdd.wouterkarl.acquire;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

public class Board {

    private List<Coordinates> tiles = new ArrayList<>();
    private Map<Company, List<Coordinates>> companyTiles = new EnumMap<>(Company.class);

    public Board() {

    }


    private Board(List<Coordinates> tiles, Map<Company,List<Coordinates>> companyTiles) {
        this.tiles = tiles;
        this.companyTiles = companyTiles;
    }

    public Board addTile(Coordinates coordinates){
        checkNotNull(coordinates);
        checkArgument(!tiles.contains(coordinates));

        List<Coordinates> newTiles = new ArrayList<>(tiles);
        newTiles.add(coordinates);
        return new Board(newTiles, companyTiles);
    }

    public Board startCompany(Coordinates coordinates, Company company){
        checkArgument(!tiles.contains(coordinates));
        checkArgument(!companyTiles.containsKey(company));

        checkArgument(companyTiles.values().stream().flatMap(Collection::stream).noneMatch(coordinates::isNextTo));

        List<Coordinates> neighbouringTiles = tiles.stream().filter(coordinates::isNextTo).collect(toList());
        checkArgument(!neighbouringTiles.isEmpty());



        List<Coordinates> tilesNewCompany = new ArrayList<>(neighbouringTiles);
        tilesNewCompany.add(coordinates);

        Map<Company, List<Coordinates>> newCompanyTiles = new EnumMap<>(companyTiles);
        newCompanyTiles.put(company, tilesNewCompany);

        List<Coordinates> newFreeTiles = tiles.stream().filter(o -> !neighbouringTiles.contains(o)).collect(toList());

        return new Board(newFreeTiles, newCompanyTiles);
    }

    public List<Company> getCompanies(){
        return new ArrayList<>(companyTiles.keySet());
    }
}
