package com.cti.statscsgo.variable;

import com.cti.statscsgo.R;

/**
 * Created by anton on 11.08.15.
 */
public class StatsConst {
    //Массив XML-тегов для HashMap
    public String MS_GUNS_KILLS[] = {"total_kills_glock", "total_kills_p250", "total_kills_fiveseven",
            "total_kills_deagle", "total_kills_elite", "total_kills_hkp2000", "total_kills_tec9",
            "total_kills_nova", "total_kills_xm1014", "total_kills_sawedoff", "total_kills_mag7",
            "total_kills_m249", "total_kills_negev", "total_kills_mac10", "total_kills_famas",
            "total_kills_ak47", "total_kills_m4a1", "total_kills_ssg08", "total_kills_sg556",
            "total_kills_aug", "total_kills_awp", "total_kills_scar20", "total_kills_g3sg1",
            "total_kills_hegrenade", "total_kills_bizon", "total_kills_galilar",
            "total_kills_knife", "total_kills_mp7", "total_kills_mp9", "total_kills_p90",
            "total_kills_ump45"
    };
    public String MS_GUNS_HITS[] = {"total_hits_glock", "total_hits_p250", "total_hits_fiveseven",
            "total_hits_deagle", "total_hits_elite", "total_hits_hkp2000", "total_hits_tec9",
            "total_hits_nova", "total_hits_xm1014", "total_hits_sawedoff", "total_hits_mag7",
            "total_hits_m249", "total_hits_negev", "total_hits_mac10", "total_hits_famas",
            "total_hits_ak47", "total_hits_m4a1", "total_hits_ssg08", "total_hits_sg556",
            "total_hits_aug", "total_hits_awp", "total_hits_scar20", "total_hits_g3sg1",
            "total_hits_hegrenade", "total_hits_bizon", "total_hits_galilar",
            "total_hits_knife", "total_hits_mp7", "total_hits_mp9", "total_hits_p90",
            "total_hits_ump45"
    };
    public String MS_GUNS_SHOTS[] = {"total_shots_glock", "total_shots_p250", "total_shots_fiveseven",
            "total_shots_deagle", "total_shots_elite", "total_shots_hkp2000", "total_shots_tec9",
            "total_shots_nova", "total_shots_xm1014", "total_shots_sawedoff", "total_shots_mag7",
            "total_shots_m249", "total_shots_negev", "total_shots_mac10", "total_shots_famas",
            "total_shots_ak47", "total_shots_m4a1", "total_shots_ssg08", "total_shots_sg556",
            "total_shots_aug", "total_shots_awp", "total_shots_scar20", "total_shots_g3sg1",
            "total_shots_hegrenade", "total_shots_bizon", "total_shots_galilar",
            "total_shots_knife", "total_shots_mp7", "total_shots_mp9", "total_shots_p90",
            "total_shots_ump45"
    };

    //weapon id
    public String MS_GUNS_ID[] = {"4", "36", "3", "1", "2", "32", "30", "35", "25", "29",
            "27", "14", "28", "17", "10", "7", "16", "40", "39", "8", "9", "38", "11", "44",
            "26", "13", "42", "33", "34", "19", "24"
    };

    public String MS_GUNS_NAMES[] = {"Glock-18", "P250", "Five-SeveN", "Desert Eagle", "Dual Berettas",
            "P2000", "Tec-9", "Nova", "XM1014", "Sawed-Off", "MAG-7", "M249", "Negev", "MAC-10",
            "FAMAS", "AK-47", "M4A4", "SSG 08", "SG 553", "AUG", "AWP", "SCAR-20", "G3SG1", "High Explosive Grenade",
            "PP-Bizon", "Galil AR", "Knife", "MP7", "MP9", "P90", "UMP-45"
    };

    //id изображения для оружия
    public int MS_ID_GUNS[] = {R.drawable.glock, R.drawable.p250, R.drawable.fiveseven,
            R.drawable.deagle, R.drawable.elite, R.drawable.p2000,
            R.drawable.tec9, R.drawable.nova,
            R.drawable.xm1014, R.drawable.sawedoff, R.drawable.mag7,
            R.drawable.m249, R.drawable.negev, R.drawable.mac10,
            R.drawable.famas, R.drawable.ak47, R.drawable.m4a4,
            R.drawable.ssg08, R.drawable.sg556, R.drawable.aug,
            R.drawable.awp, R.drawable.scar20, R.drawable.g3sg1,
            R.drawable.hegrenade, R.drawable.bizon, R.drawable.galilar,
            R.drawable.knife, R.drawable.mp7, R.drawable.mp9,
            R.drawable.p90, R.drawable.ump
    };

    //Константы для статистики по картам MapStats.java

    public int MS_MAPS_IMG[] = { R.drawable.cs_assault, R.drawable.cs_italy,
            R.drawable.cs_office, R.drawable.de_aztec, R.drawable.de_cbble,
            R.drawable.de_dust2, R.drawable.de_dust, R.drawable.de_inferno,
            R.drawable.de_nuke, R.drawable.no_map, R.drawable.de_train,
            R.drawable.de_lake, R.drawable.de_safehouse, R.drawable.de_stmarc,
            R.drawable.de_bank, R.drawable.de_train, R.drawable.ar_shoots,
            R.drawable.ar_baggage, R.drawable.ar_monastery, R.drawable.de_vertigo,
            R.drawable.cs_militia
    };

    public String MS_MAPS_WINS[] = {"total_wins_map_cs_assault","total_wins_map_cs_italy",
            "total_wins_map_cs_office","total_wins_map_de_aztec","total_wins_map_de_cbble",
            "total_wins_map_de_dust2", "total_wins_map_de_dust","total_wins_map_de_inferno",
            "total_wins_map_de_nuke","total_wins_map_de_piranesi","total_wins_map_de_train",
            "total_wins_map_de_lake","total_wins_map_de_safehouse","total_wins_map_de_stmarc",
            "total_wins_map_de_bank","total_wins_map_de_shorttrain","total_wins_map_ar_shoots",
            "total_wins_map_ar_baggage","total_wins_map_ar_monastery","total_wins_map_de_vertigo",
            "total_wins_map_cs_militia"
    };

    public String MS_MAPS_ROUNDS[] ={ "total_rounds_map_cs_assault","total_rounds_map_cs_italy",
            "total_rounds_map_cs_office","total_rounds_map_de_aztec","total_rounds_map_de_cbble",
            "total_rounds_map_de_dust2","total_rounds_map_de_dust","total_rounds_map_de_inferno",
            "total_rounds_map_de_nuke","total_rounds_map_de_piranesi", "total_rounds_map_de_train",
            "total_rounds_map_de_lake","total_rounds_map_de_safehouse","total_rounds_map_de_stmarc",
            "total_rounds_map_de_bank","total_rounds_map_de_shorttrain","total_rounds_map_ar_shoots",
            "total_rounds_map_ar_baggage","total_rounds_map_ar_monastery","total_rounds_map_de_vertigo",
            "total_rounds_map_cs_militia"
    };

    public String MS_MAPS[] ={ "cs_assault","cs_italy","cs_office","de_aztec","de_cbble",
            "de_dust2","de_dust","de_inferno","de_nuke","de_piranesi", "de_train",
            "de_lake","de_safehouse","de_stmarc","de_bank","de_shorttrain","ar_shoots",
            "ar_baggage","ar_monastery","de_vertigo","cs_militia"
    };
}
