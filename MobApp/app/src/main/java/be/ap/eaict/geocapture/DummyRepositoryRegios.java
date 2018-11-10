package be.ap.eaict.geocapture;

import java.util.ArrayList;
import java.util.List;

import be.ap.eaict.geocapture.Model.Locatie;
import be.ap.eaict.geocapture.Model.Puzzel;
import be.ap.eaict.geocapture.Model.Regio;

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
                                "station",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat wordt het meetst verkocht in starbucks", "drankje 3"));
                                    add(new Puzzel("hoe ver kan je geraken met een enkele trein(locatie)", "destinatie x"));
                                    add(new Puzzel("wa uur is locket x open", "4:20"));
                                }},
                                51.217263f,4.421034f
                        ));
                        add(new Locatie(
                                "kathedraal",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld x", "5"));
                                    add(new Puzzel("wanneer is beeld x", "2"));
                                    add(new Puzzel("waarom is dit een ding", "nee"));
                                }},
                                51.220236f, 4.402139f
                        ));
                        add(new Locatie(
                                "KOT",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld x", "5"));
                                    add(new Puzzel("wanneer is beeld x", "2"));
                                    add(new Puzzel("waarom is dit een ding", "nee"));
                                }},
                                51.206264f, 4.413934f
                        ));
                    }},
                    60*60*5
            ));
            add(new Regio(
                    "Brasschaat",
                    new ArrayList<Locatie>(){{
                        add(new Locatie(
                                "zwembad",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wie is hier het meest bekend", "yorick"));
                                    add(new Puzzel("wat doet deze persoon", "lijntje coca cola"));
                                    add(new Puzzel("waarom", "steen"));
                                }},
                                51.284836f, 4.500597f
                        ));
                        add(new Locatie(
                                "park",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld y", "19"));
                                    add(new Puzzel("wanneer is beeld t", "2"));
                                    add(new Puzzel("waarom is dit een iets", "misschien"));
                                }},
                                51.288343f, 4.495046f
                        ));
                        add(new Locatie(
                                "golfclub",
                                new ArrayList<Puzzel>(){{
                                    add(new Puzzel("wat is beeld y", "19"));
                                    add(new Puzzel("wanneer is beeld t", "2"));
                                    add(new Puzzel("waarom is dit een iets", "misschien"));
                                }},
                                51.296294f, 4.516631f
                        ));
                    }},
                    60*60*5
            ));
        }};

        return regios;
    }
}
