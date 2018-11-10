package be.ap.eaict.geocapture;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class DummyRepositoryRegios implements IDummyRepositoryRegios{

    private static IDummyRepositoryRegios repo = null;

    private static IDummyRepositoryRegios getInstance() {
        if (repo == null)
        {
            repo = new DummyRepositoryRegios();
        }
        return repo;
    }

    @Override
    public List<Regio> getRegios() {
        List<Regio> regios = new ArrayList<Regio>(){{
            add(new Regio(
                    "Antwerpen",
                    new ArrayList<Locatie>(){{
                        add(new Locatie(
                                "kerk",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat wordt het meetst verkocht in starbucks", "drankje 3"));
                                    add(new Puzzel("hoe ver kan je geraken met een enkele trein(locatie)", "destinatie x"));
                                    add(new Puzzel("wa uur is locket x open", "5:50"));
                                }},
                                51.217263f,4.421034f
                        ));
                        add(new Locatie(
                                "kerk",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld x", "5"));
                                    add(new Puzzel("wanneer is beeld x", "2"));
                                    add(new Puzzel("waarom is dit een ding", "nee"));
                                }},
                                51.217263f,4.421034f
                        ));
                    }},
                    60*60*5
            ));
            add(new Regio(
                    "Brasschaat",
                    new ArrayList<Locatie>(){{
                        add(new Locatie(
                                "kerk",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wie is hier het meest bekend", "yorick"));
                                    add(new Puzzel("wat doet deze persoon", "lijntje coca cola"));
                                    add(new Puzzel("waarom", "steen"));
                                }},
                                51.217263f,4.421034f
                        ));
                        add(new Locatie(
                                "kerk",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld y", "19"));
                                    add(new Puzzel("wanneer is beeld t", "2"));
                                    add(new Puzzel("waarom is dit een iets", "misschien"));
                                }},
                                51.217263f,4.421034f
                        ));
                    }},
                    60*60*5
            ));
        }};

        return regios;
    }
}
