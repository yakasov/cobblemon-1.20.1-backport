/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.client.render.models.blockbench.repository

import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen1.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen2.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen3.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen4.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen5.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen6.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen7.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen8.*
import com.cobblemon.mod.common.client.render.models.blockbench.pokemon.gen9.*
import com.cobblemon.mod.common.client.render.models.blockbench.pose.Bone
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.util.cobblemonResource
import com.google.gson.JsonObject

object PokemonModelRepository : VaryingModelRepository<PokemonEntity, PokemonPoseableModel>() {
    override val title = "Pokémon"
    override val type = "pokemon"
    override val variationDirectories = listOf("bedrock/$type/resolvers", "bedrock/species")
    override val poserDirectories = listOf("bedrock/$type/posers", "bedrock/posers")
    override val modelDirectories = listOf("bedrock/$type/models", "bedrock/models")
    override val animationDirectories: List<String> = listOf("bedrock/animations", "bedrock/$type/animations")
    override val isForLivingEntityRenderer = true

    override val fallback = cobblemonResource("substitute")

    override fun registerInBuiltPosers() {
        inbuilt("squirtle", ::SquirtleModel)
        inbuilt("wartortle", ::WartortleModel)
        inbuilt("caterpie", ::CaterpieModel)
        inbuilt("metapod", ::MetapodModel)
        inbuilt("butterfree", ::ButterfreeModel)
        inbuilt("weedle", ::WeedleModel)
        inbuilt("kakuna", ::KakunaModel)
        inbuilt("beedrill", ::BeedrillModel)
        inbuilt("rattata", ::RattataModel)
        inbuilt("raticate", ::RaticateModel)
        inbuilt("rattata_alolan", ::RattataAlolanModel)
        inbuilt("raticate_alolan", ::RaticateAlolanModel)
        inbuilt("eevee", ::EeveeModel)
        inbuilt("pidgey", ::PidgeyModel)
        inbuilt("pidgeotto", ::PidgeottoModel)
        inbuilt("pidgeot", ::PidgeotModel)
        inbuilt("diglett", ::DiglettModel)
        inbuilt("dugtrio", ::DugtrioModel)
        inbuilt("zubat", ::ZubatModel)
        inbuilt("cleffa", ::CleffaModel)
        inbuilt("clefable", ::ClefableModel)
        inbuilt("clefairy", ::ClefairyModel)
        inbuilt("krabby", ::KrabbyModel)
        inbuilt("paras", ::ParasModel)
        inbuilt("parasect", ::ParasectModel)
        inbuilt("mankey", ::MankeyModel)
        inbuilt("primeape", ::PrimeapeModel)
        inbuilt("oddish", ::OddishModel)
        inbuilt("gloom", ::GloomModel)
        inbuilt("vileplume", ::VileplumeModel)
        inbuilt("bellossom", ::BellossomModel)
        inbuilt("voltorb", ::VoltorbModel)
        inbuilt("electrode", ::ElectrodeModel)
        inbuilt("ekans", ::EkansModel)
        inbuilt("machop", ::MachopModel)
        inbuilt("machoke", ::MachokeModel)
        inbuilt("machamp", ::MachampModel)
        inbuilt("arbok", ::ArbokModel)
        inbuilt("arcanine", ::ArcanineModel)
        inbuilt("articuno", ::ArticunoModel)
        inbuilt("chansey", ::ChanseyModel)
        inbuilt("crobat", ::CrobatModel)
        inbuilt("cubone", ::CuboneModel)
        inbuilt("dewgong", ::DewgongModel)
        inbuilt("ditto", ::DittoModel)
        inbuilt("dodrio", ::DodrioModel)
        inbuilt("doduo", ::DoduoModel)
        inbuilt("dragonair", ::DragonairModel)
        inbuilt("dragonite", ::DragoniteModel)
        inbuilt("dratini", ::DratiniModel)
        inbuilt("drowzee", ::DrowzeeModel)
        inbuilt("electabuzz", ::ElectabuzzModel)
        inbuilt("exeggcute", ::ExeggcuteModel)
        inbuilt("exeggutor", ::ExeggutorModel)
        inbuilt("exeggutor_alolan", ::ExeggutorAlolanModel)
        inbuilt("farfetchd", ::FarfetchdModel)
        inbuilt("farfetchd_galarian", ::FarfetchdGalarianModel)
        inbuilt("fearow", ::FearowModel)
        inbuilt("flareon", ::FlareonModel)
        inbuilt("gengar", ::GengarModel)
        inbuilt("geodude", ::GeodudeModel)
        inbuilt("golbat", ::GolbatModel)
        inbuilt("golduck", ::GolduckModel)
        inbuilt("golem", ::GolemModel)
        inbuilt("graveler", ::GravelerModel)
        inbuilt("grimer", ::GrimerModel)
        inbuilt("growlithe", ::GrowlitheModel)
        inbuilt("hitmonchan", ::HitmonchanModel)
        inbuilt("hitmonlee", ::HitmonleeModel)
        inbuilt("hypno", ::HypnoModel)
        inbuilt("jolteon", ::JolteonModel)
        inbuilt("jynx", ::JynxModel)
        inbuilt("kabuto", ::KabutoModel)
        inbuilt("kabutops", ::KabutopsModel)
        inbuilt("kadabra", ::KadabraModel)
        inbuilt("kangaskhan", ::KangaskhanModel)
        inbuilt("koffing", ::KoffingModel)
        inbuilt("krabby", ::KrabbyModel)
        inbuilt("lickitung", ::LickitungModel)
        inbuilt("magnemite", ::MagnemiteModel)
        inbuilt("magneton", ::MagnetonModel)
        inbuilt("marowak", ::MarowakModel)
        inbuilt("meowth", ::MeowthModel)
        inbuilt("mew", ::MewModel)
        inbuilt("mewtwo", ::MewtwoModel)
        inbuilt("moltres", ::MoltresModel)
        inbuilt("mrmime", ::MrmimeModel)
        inbuilt("muk", ::MukModel)
        inbuilt("nidoking", ::NidokingModel)
        inbuilt("nidoqueen", ::NidoqueenModel)
        inbuilt("nidoranf", ::NidoranfModel)
        inbuilt("nidoranm", ::NidoranmModel)
        inbuilt("nidorina", ::NidorinaModel)
        inbuilt("nidorino", ::NidorinoModel)
        inbuilt("ninetales", ::NinetalesModel)
        inbuilt("omanyte", ::OmanyteModel)
        inbuilt("omastar", ::OmastarModel)
        inbuilt("persian", ::PersianModel)
        inbuilt("pikachu", ::PikachuModel)
        inbuilt("pinsir", ::PinsirModel)
        inbuilt("poliwag", ::PoliwagModel)
        inbuilt("politoed", ::PolitoedModel)
        inbuilt("ponyta", ::PonytaModel)
        inbuilt("psyduck", ::PsyduckModel)
        inbuilt("raichu", ::RaichuModel)
        inbuilt("rapidash", ::RapidashModel)
        inbuilt("sandshrew", ::SandshrewModel)
        inbuilt("sandslash", ::SandslashModel)
        inbuilt("scyther", ::ScytherModel)
        inbuilt("seel", ::SeelModel)
        inbuilt("slowbro", ::SlowbroModel)
        inbuilt("slowpoke", ::SlowpokeModel)
        inbuilt("spearow", ::SpearowModel)
        inbuilt("starmie", ::StarmieModel)
        inbuilt("staryu", ::StaryuModel)
        inbuilt("steelix", ::SteelixModel)
        inbuilt("tangela", ::TangelaModel)
        inbuilt("tauros", ::TaurosModel)
        inbuilt("tentacool", ::TentacoolModel)
        inbuilt("tentacruel", ::TentacruelModel)
        inbuilt("vaporeon", ::VaporeonModel)
        inbuilt("venomoth", ::VenomothModel)
        inbuilt("venonat", ::VenonatModel)
        inbuilt("vulpix", ::VulpixModel)
        inbuilt("weezing", ::WeezingModel)
        inbuilt("zapdos", ::ZapdosModel)
        inbuilt("elekid", ::ElekidModel)
        inbuilt("pichu", ::PichuModel)
        inbuilt("smoochum", ::SmoochumModel)
        inbuilt("hitmontop", ::HitmontopModel)
        inbuilt("electivire", ::ElectivireModel)
        inbuilt("glaceon", ::GlaceonModel)
        inbuilt("leafeon", ::LeafeonModel)
        inbuilt("lickilicky", ::LickilickyModel)
        inbuilt("mimejr", ::MimejrModel)
        inbuilt("scizor", ::ScizorModel)
        inbuilt("tangrowth", ::TangrowthModel)
        inbuilt("sylveon", ::SylveonModel)
        inbuilt("umbreon", ::UmbreonModel)
        inbuilt("espeon", ::EspeonModel)
        inbuilt("blissey", ::BlisseyModel)
        inbuilt("piloswine", ::PiloswineModel)
        inbuilt("quagsire", ::QuagsireModel)
        inbuilt("slowking", ::SlowkingModel)
        inbuilt("swinub", ::SwinubModel)
        inbuilt("wooper", ::WooperModel)
        inbuilt("wooper_paldean", ::WooperPaldeanModel)
        inbuilt("yanma", ::YanmaModel)
        inbuilt("blaziken", ::BlazikenModel)
        inbuilt("combusken", ::CombuskenModel)
        inbuilt("minun", ::MinunModel)
        inbuilt("plusle", ::PlusleModel)
        inbuilt("rayquaza", ::RayquazaModel)
        inbuilt("torchic", ::TorchicModel)
        inbuilt("bibarel", ::BibarelModel)
        inbuilt("bidoof", ::BidoofModel)
        inbuilt("buneary", ::BunearyModel)
        inbuilt("lopunny", ::LopunnyModel)
        inbuilt("mamoswine", ::MamoswineModel)
        inbuilt("pachirisu", ::PachirisuModel)
        inbuilt("piplup", ::PiplupModel)
        inbuilt("yanmega", ::YanmegaModel)
        inbuilt("basculin", ::BasculinModel)
        inbuilt("emolga", ::EmolgaModel)
        inbuilt("maractus", ::MaractusModel)
        inbuilt("bounsweet", ::BounsweetModel)
        inbuilt("dartrix", ::DartrixModel)
        inbuilt("decidueye", ::DecidueyeModel)
        inbuilt("mimikyu", ::MimikyuModel)
        inbuilt("naganadel", ::NaganadelModel)
        inbuilt("poipole", ::PoipoleModel)
        inbuilt("rowlet", ::RowletModel)
        inbuilt("steenee", ::SteeneeModel)
        inbuilt("tsareena", ::TsareenaModel)
        inbuilt("centiskorch", ::CentiskorchModel)
        inbuilt("sizzlipede", ::SizzlipedeModel)
        inbuilt("kleavor", ::KleavorModel)
        inbuilt("pyukumuku", ::PyukumukuModel)
        inbuilt("deerling", ::DeerlingModel)
        inbuilt("sawsbuck", ::SawsbuckModel)
        inbuilt("sableye", ::SableyeModel)
        inbuilt("natu", ::NatuModel)
        inbuilt("xatu", ::XatuModel)
        inbuilt("wailmer", ::WailmerModel)
        inbuilt("wailord", ::WailordModel)
        inbuilt("murkrow", ::MurkrowModel)
        inbuilt("honchkrow", ::HonchkrowModel)
        inbuilt("nacli", :: NacliModel)
        inbuilt("naclstack", :: NaclstackModel)
        inbuilt("garganacl", ::GarganaclModel)
        inbuilt("dhelmise", :: DhelmiseModel)
        inbuilt("alcremie", :: AlcremieModel)
        inbuilt("milcery", :: MilceryModel)
        inbuilt("turtwig", :: TurtwigModel)
        inbuilt("grotle", :: GrotleModel)
        inbuilt("torterra", :: TorterraModel)
        inbuilt("torterra_cherry", :: TorterraCherryModel)
        inbuilt("xerneas", :: XerneasModel)
        inbuilt("klink", :: KlinkModel)
        inbuilt("klang", :: KlangModel)
        inbuilt("klinklang", :: KlinklangModel)
        inbuilt("morelull", :: MorelullModel)
        inbuilt("shiinotic", :: ShiinoticModel)
        inbuilt("treecko", :: TreeckoModel)
        inbuilt("grovyle", :: GrovyleModel)
        inbuilt("sceptile", :: SceptileModel)
        inbuilt("honedge", :: HonedgeModel)
        inbuilt("spiritomb", :: SpiritombModel)
        inbuilt("chespin", :: ChespinModel)
        inbuilt("quilladin", :: QuilladinModel)
        inbuilt("chesnaught", :: ChesnaughtModel)
        inbuilt("elgyem", :: ElgyemModel)
        inbuilt("beheeyem", :: BeheeyemModel)
        inbuilt("pineco", :: PinecoModel)
        inbuilt("forretress", :: ForretressModel)
        inbuilt("doublade", :: DoubladeModel)
        inbuilt("aegislash", :: AegislashModel)
        inbuilt("lotad", :: LotadModel)
        inbuilt("lombre", :: LombreModel)
        inbuilt("ludicolo", :: LudicoloModel)
        inbuilt("golett", :: GolettModel)
        inbuilt("golurk", :: GolurkModel)
        inbuilt("stantler", :: StantlerModel)
        inbuilt("wyrdeer", :: WyrdeerModel)
        inbuilt("sneasel", :: SneaselModel)
        inbuilt("weavile", :: WeavileModel)
        inbuilt("bergmite", :: BergmiteModel)
        inbuilt("avalugg", :: AvaluggModel)
        inbuilt("misdreavus", :: MisdreavusModel)
        inbuilt("mismagius", :: MismagiusModel)
        inbuilt("whismur", :: WhismurModel)
        inbuilt("loudred", :: LoudredModel)
        inbuilt("exploud", :: ExploudModel)
        inbuilt("luvdisc", :: LuvdiscModel)
        inbuilt("cryogonal", :: CryogonalModel)
        inbuilt("sigilyph", :: SigilyphModel)
        inbuilt("pumpkaboo", :: PumpkabooModel)
        inbuilt("gourgeist", :: GourgeistModel)
        inbuilt("eiscue", :: EiscueModel)
        inbuilt("wooloo", :: WoolooModel)
        inbuilt("dubwool", :: DubwoolModel)
        inbuilt("chimchar", :: ChimcharModel)
        inbuilt("monferno", :: MonfernoModel)
        inbuilt("infernape", :: InfernapeModel)
        inbuilt("seedot", ::SeedotModel)
        inbuilt("nuzleaf", ::NuzleafModel)
        inbuilt("shiftry", ::ShiftryModel)
        inbuilt("kricketot", ::KricketotModel)
        inbuilt("kricketune", ::KricketuneModel)
        inbuilt("heatmor", ::HeatmorModel)
        inbuilt("durant", ::DurantModel)
        inbuilt("sharpedo", ::SharpedoModel)
        inbuilt("mawile", ::MawileModel)
        inbuilt("walkingwake", ::WalkingwakeModel)
        inbuilt("ironleaves", ::IronleavesModel)
        inbuilt("miltank", ::MiltankModel)
        inbuilt("froakie", ::FroakieModel)
        inbuilt("frogadier", ::FrogadierModel)
        inbuilt("greninja", ::GreninjaModel)
        inbuilt("grookey", ::GrookeyModel)
        inbuilt("thwackey", ::ThwackeyModel)
        inbuilt("rillaboom", ::RillaboomModel)
        inbuilt("raboot", ::RabootModel)
        inbuilt("oshawott", ::OshawottModel)
        inbuilt("dewott", ::DewottModel)
        inbuilt("samurott", ::SamurottModel)
        inbuilt("snivy", ::SnivyModel)
        inbuilt("servine", ::ServineModel)
        inbuilt("serperior", ::SerperiorModel)
        inbuilt("slugma", ::SlugmaModel)
        inbuilt("magcargo", ::MagcargoModel)
        inbuilt("slugma_shiny", ::SlugmaShinyModel)
        inbuilt("magcargo_shiny", ::MagcargoShinyModel)
        inbuilt("nosepass", ::NosepassModel)
        inbuilt("probopass", ::ProbopassModel)
        inbuilt("chinchou", ::ChinchouModel)
        inbuilt("clamperl", ::ClamperlModel)
        inbuilt("huntail", ::HuntailModel)
        inbuilt("gorebyss", ::GorebyssModel)
        inbuilt("spinarak", ::SpinarakModel)
        inbuilt("ariados", ::AriadosModel)
        inbuilt("shuckle", ::ShuckleModel)
        inbuilt("taillow", ::TaillowModel)
        inbuilt("swellow", ::SwellowModel)
        inbuilt("mudbray", ::MudbrayModel)
        inbuilt("mudsdale", ::MudsdaleModel)
        inbuilt("comfey", ::ComfeyModel)
        inbuilt("tandemaus", ::TandemausModel)
        inbuilt("maushold", ::MausholdModel)
        inbuilt("mausholdfour", ::MausholdfourModel)
        inbuilt("varoom", ::VaroomModel)
        inbuilt("revavroom", ::RevavroomModel)
        inbuilt("lanturn", ::LanturnModel)
        inbuilt("chingling", ::ChinglingModel)
        inbuilt("chimecho", ::ChimechoModel)
        inbuilt("fidough", ::FidoughModel)
        inbuilt("dachsbun", ::DachsbunModel)
        inbuilt("chatot", ::ChatotModel)
        inbuilt("gligar", ::GligarModel)
        inbuilt("gliscor", ::GliscorModel)
        inbuilt("poochyena", ::PoochyenaModel)
        inbuilt("mightyena", ::MightyenaModel)
        inbuilt("floragato", ::FloragatoModel)
        inbuilt("meowscarada", ::MeowscaradaModel)
        inbuilt("shroomish", ::ShroomishModel)
        inbuilt("breloom", ::BreloomModel)
        inbuilt("charcadet", ::CharcadetModel)
        inbuilt("armarouge", ::ArmarougeModel)
        inbuilt("ceruledge", ::CeruledgeModel)
        inbuilt("flittle", ::FlittleModel)
        inbuilt("espathra", ::EspathraModel)
        inbuilt("surskit", ::SurskitModel)
        inbuilt("masquerain", ::MasquerainModel)
        inbuilt("cutiefly", ::CutieflyModel)
        inbuilt("ribombee", ::RibombeeModel)
        inbuilt("carnivine", ::CarnivineModel)
        inbuilt("falinks", ::FalinksModel)
        inbuilt("stufful", ::StuffulModel)
        inbuilt("bewear", ::BewearModel)
        inbuilt("scatterbug", ::ScatterbugModel)
        inbuilt("spewpa", ::SpewpaModel)
        inbuilt("vivillon", ::VivillonModel)
        inbuilt("barboach", ::BarboachModel)
        inbuilt("whiscash", ::WhiscashModel)
        inbuilt("combee", ::CombeeModel)
        inbuilt("vespiquen", ::VespiquenModel)
        inbuilt("lillipup", ::LillipupModel)
        inbuilt("herdier", ::HerdierModel)
        inbuilt("stoutland", ::StoutlandModel)
        inbuilt("sirfetchd", ::SirfetchdModel)
        inbuilt("rookidee", ::RookideeModel)
        inbuilt("corvisquire", ::CorvisquireModel)
        inbuilt("corviknight", ::CorviknightModel)
        inbuilt("duskull", ::DuskullModel)
        inbuilt("dusclops", ::DusclopsModel)
        inbuilt("dusknoir", ::DusknoirModel)
        inbuilt("nickit", ::NickitModel)
        inbuilt("thievul", ::ThievulModel)
        inbuilt("cacnea", ::CacneaModel)
        inbuilt("cacturne", ::CacturneModel)
        inbuilt("glimmet", ::GlimmetModel)
        inbuilt("glimmora", ::GlimmoraModel)
        inbuilt("bonsly", ::BonslyModel)
        inbuilt("sudowoodo", ::SudowoodoModel)
        inbuilt("bouffalant", ::BouffalantModel)
        inbuilt("cetoddle", ::CetoddleModel)
        inbuilt("cetitan", ::CetitanModel)
        inbuilt("venipede", ::VenipedeModel)
        inbuilt("whirlipede", ::WhirlipedeModel)
        inbuilt("scolipede", ::ScolipedeModel)
        inbuilt("aipom", ::AipomModel)
        inbuilt("ambipom", ::AmbipomModel)
        inbuilt("hoothoot", ::HoothootModel)
        inbuilt("noctowl", ::NoctowlModel)
        inbuilt("wingull", ::WingullModel)
        inbuilt("pelipper", ::PelipperModel)
        inbuilt("shinx", ::ShinxModel)
        inbuilt("luxio", ::LuxioModel)
        inbuilt("luxray", ::LuxrayModel)
        inbuilt("numel", ::NumelModel)
        inbuilt("camerupt", ::CameruptModel)
        inbuilt("vulpix_alolan", ::VulpixAlolanModel)
        inbuilt("ninetales_alolan", ::NinetalesAlolanModel)
        inbuilt("roggenrola", ::RoggenrolaModel)
        inbuilt("boldore", ::BoldoreModel)
        inbuilt("gigalith", ::GigalithModel)
        inbuilt("yamask", ::YamaskModel)
        inbuilt("cofagrigus", ::CofagrigusModel)
        inbuilt("mareep", ::MareepModel)
        inbuilt("flaaffy", ::FlaaffyModel)
        inbuilt("ampharos", ::AmpharosModel)
        inbuilt("patrat", ::PatratModel)
        inbuilt("watchog", ::WatchogModel)
        inbuilt("skrelp", ::SkrelpModel)
        inbuilt("dragalge", ::DragalgeModel)
        inbuilt("bunnelby", ::BunnelbyModel)
        inbuilt("arrokuda", ::ArrokudaModel)
        inbuilt("barraskewda", ::BarraskewdaModel)
        inbuilt("shroodle", ::ShroodleModel)
        inbuilt("grafaiai", ::GrafaiaiModel)
        inbuilt("squawkabilly", ::SquawkabillyModel)
        inbuilt("annihilape", ::AnnihilapeModel)
        inbuilt("ponyta_galarian", ::PonytaGalarianModel)
        inbuilt("rapidash_galarian", ::RapidashGalarianModel)
        inbuilt("volbeat", ::VolbeatModel)
        inbuilt("illumise", ::IllumiseModel)
        inbuilt("yamper", ::YamperModel)
        inbuilt("boltund", ::BoltundModel)
        inbuilt("tinkatink", ::TinkatinkModel)
        inbuilt("tinkatuff", ::TinkatuffModel)
        inbuilt("fuecoco", :: FuecocoModel)
        inbuilt("crocalor", :: CrocalorModel)
        inbuilt("skeledirge", :: SkeledirgeModel)
        inbuilt("quaxly", :: QuaxlyModel)
        inbuilt("quaxwell", :: QuaxwellModel)
        inbuilt("quaquaval", :: QuaquavalModel)
        inbuilt("snubbull", :: SnubbullModel)
        inbuilt("granbull", :: GranbullModel)
        inbuilt("maschiff", :: MaschiffModel)
        inbuilt("mabosstiff", :: MabosstiffModel)
        inbuilt("phanpy", :: PhanpyModel)
        inbuilt("donphan", :: DonphanModel)
        inbuilt("buizel", :: BuizelModel)
        inbuilt("floatzel", :: FloatzelModel)
        inbuilt("zigzagoon", :: ZigzagoonModel)
        inbuilt("linoone", :: LinooneModel)
        inbuilt("zigzagoon_galarian", :: ZigzagoonGalarianModel)
        inbuilt("linoone_galarian", :: LinooneGalarianModel)
        inbuilt("obstagoon", :: ObstagoonModel)
        inbuilt("cottonee", :: CottoneeModel)
        inbuilt("whimsicott", :: WhimsicottModel)
        inbuilt("wishiwashi_solo", :: WishiwashiSoloModel)
        inbuilt("wishiwashi_schooling", :: WishiwashiSchoolingModel)
        inbuilt("meowth_alolan", ::MeowthAlolanModel)
        inbuilt("meowth_galarian", ::MeowthGalarianModel)
        inbuilt("persian_alolan", ::PersianAlolanModel)
        inbuilt("perrserker", ::PerrserkerModel)
        inbuilt("starly", ::StarlyModel)
        inbuilt("staravia", ::StaraviaModel)
        inbuilt("staraptor", ::StaraptorModel)
        inbuilt("komala", ::KomalaModel)
        inbuilt("phantump", ::PhantumpModel)
        inbuilt("totodile", ::TotodileModel)
        inbuilt("croconaw", ::CroconawModel)
        inbuilt("feraligatr", ::FeraligatrModel)
        inbuilt("cyndaquil", ::CyndaquilModel)
        inbuilt("quilava", ::QuilavaModel)
        inbuilt("typhlosion", ::TyphlosionModel)
        inbuilt("chikorita", ::ChikoritaModel)
        inbuilt("bayleef", ::BayleefModel)
        inbuilt("meganium", ::MeganiumModel)
        inbuilt("fletchling", ::FletchlingModel)
        inbuilt("fletchinder", ::FletchinderModel)
        inbuilt("talonflame", ::TalonflameModel)
        inbuilt("crabrawler", ::CrabrawlerModel)
        inbuilt("crabominable", ::CrabominableModel)
        inbuilt("wimpod", ::WimpodModel)
        inbuilt("golisopod", ::GolisopodModel)
        inbuilt("shedinja", ::ShedinjaModel)
        inbuilt("ralts", ::RaltsModel)
        inbuilt("kirlia", ::KirliaModel)
        inbuilt("gardevoir", ::GardevoirModel)
        inbuilt("gallade", ::GalladeModel)
        inbuilt("beldum", ::BeldumModel)
        inbuilt("metang", ::MetangModel)
        inbuilt("metagross", ::MetagrossModel)
        inbuilt("ursaluna", ::UrsalunaModel)
        inbuilt("lechonk", ::LechonkModel)
        inbuilt("oinkologne_male", ::OinkologneMaleModel)
        inbuilt("oinkologne_female", ::OinkologneFemaleModel)
        inbuilt("pidove", ::PidoveModel)
        inbuilt("tranquill", ::TranquillModel)
        inbuilt("unfezant", ::UnfezantModel)
        inbuilt("timburr", ::TimburrModel)
        inbuilt("gurdurr", ::GurdurrModel)
        inbuilt("conkeldurr", ::ConkeldurrModel)
        inbuilt("clodsire", ::ClodsireModel)
        inbuilt("teddiursa", ::TeddiursaModel)
        inbuilt("ursaring", ::UrsaringModel)
        inbuilt("litwick", ::LitwickModel)
        inbuilt("lampent", ::LampentModel)
        inbuilt("chandelure", ::ChandelureModel)
        inbuilt("gimmighoulchest", ::GimmighoulChestModel)
        inbuilt("gholdengo", ::GholdengoModel)
        inbuilt("drifloon", ::DrifloonModel)
        inbuilt("drifblim", ::DrifblimModel)
        inbuilt("lileep", ::LileepModel)
        inbuilt("cradily", ::CradilyModel)
        inbuilt("tirtouga", ::TirtougaModel)
        inbuilt("carracosta", ::CarracostaModel)
        inbuilt("arctovish", ::ArctovishModel)
        inbuilt("dracovish", ::DracovishModel)
        inbuilt("arctozolt", ::ArctozoltModel)
        inbuilt("dracozolt", ::DracozoltModel)
        inbuilt("shieldon", ::ShieldonModel)
        inbuilt("bastiodon", ::BastiodonModel)
        inbuilt("cranidos", ::CranidosModel)
        inbuilt("rampardos", ::RampardosModel)
        inbuilt("basculegion", ::BasculegionModel)
        inbuilt("tyrunt", ::TyruntModel)
        inbuilt("tyrantrum", ::TyrantrumModel)
        inbuilt("anorith", ::AnorithModel)
        inbuilt("armaldo", ::ArmaldoModel)
        inbuilt("archen", ::ArchenModel)
        inbuilt("archeops", ::ArcheopsModel)
        inbuilt("aron", ::AronModel)
        inbuilt("lairon", ::LaironModel)
        inbuilt("aggron", ::AggronModel)
        inbuilt("hippopotas", ::HippopotasModel)
        inbuilt("hippowdon", ::HippowdonModel)
        inbuilt("zorua", ::ZoruaModel)
        inbuilt("zorua_hisuian", ::ZoruaHisuianModel)
        inbuilt("zoroark", ::ZoroarkModel)
        inbuilt("zoroark_hisuian", ::ZoroarkHisuianModel)
        inbuilt("gossifleur", ::GossifleurModel)
        inbuilt("eldegoss", ::EldegossModel)
        inbuilt("amaura", ::AmauraModel)
        inbuilt("aurorus", ::AurorusModel)
        inbuilt("voltorb_hisuian", ::VoltorbHisuianModel)
        inbuilt("electrode_hisuian", ::ElectrodeHisuianModel)
        inbuilt("sentret", ::SentretModel)
        inbuilt("qwilfish", ::QwilfishModel)
        inbuilt("qwilfish_hisuian", ::QwilfishHisuianModel)
        inbuilt("overqwil", ::OverqwilModel)
        inbuilt("sneasel_hisuian", ::SneaselHisuianModel)
        inbuilt("sneasler", ::SneaslerModel)
        inbuilt("tropius", ::TropiusModel)
        inbuilt("petilil", ::PetililModel)
        inbuilt("lilligant", ::LilligantModel)
        inbuilt("petilil_hisui_bias", ::PetililHisuiBiasModel)
        inbuilt("lilligant_hisuian", ::LilligantHisuianModel)
        inbuilt("darumaka", ::DarumakaModel)
        inbuilt("darmanitan", ::DarmanitanModel)
        inbuilt("darmanitan_zen", ::DarmanitanZenModel)
        inbuilt("turtonator", ::TurtonatorModel)
        inbuilt("stonjourner", ::StonjournerModel)
        inbuilt("cufant", ::CufantModel)
        inbuilt("copperajah", ::CopperajahModel)
        inbuilt("budew", ::BudewModel)
        inbuilt("roselia", ::RoseliaModel)
        inbuilt("roserade", ::RoseradeModel)
        inbuilt("woobat", ::WoobatModel)
        inbuilt("swoobat", ::SwoobatModel)
        inbuilt("sandile", ::SandileModel)
        inbuilt("krokorok", ::KrokorokModel)
        inbuilt("frillish", ::FrillishModel)
        inbuilt("jellicent", ::JellicentModel)
        inbuilt("cubchoo", ::CubchooModel)
        inbuilt("beartic", ::BearticModel)
        inbuilt("deino", ::DeinoModel)
        inbuilt("zweilous", ::ZweilousModel)
        inbuilt("larvesta", ::LarvestaModel)
        inbuilt("volcarona", ::VolcaronaModel)
        inbuilt("dreepy", ::DreepyModel)
        inbuilt("drakloak", ::DrakloakModel)
        inbuilt("dragapult", ::DragapultModel)
        inbuilt("diglett_alolan", ::DiglettAlolanModel)
        inbuilt("dugtrio_alolan", ::DugtrioAlolanModel)
        inbuilt("makuhita", ::MakuhitaModel)
        inbuilt("hariyama", ::HariyamaModel)
        inbuilt("alomomola", ::AlomomolaModel)
        inbuilt("ferroseed", ::FerroseedModel)
        inbuilt("ferrothorn", ::FerrothornModel)
        inbuilt("carbink", ::CarbinkModel)
        inbuilt("goomy", ::GoomyModel)
        inbuilt("goomy_hisui_bias", ::GoomyHisuiBiasModel)
        inbuilt("sliggoo", ::SliggooModel)
        inbuilt("sliggoo_hisuian", ::SliggooHisuianModel)
        inbuilt("goodra", ::GoodraModel)
        inbuilt("goodra_hisuian", ::GoodraHisuianModel)
        inbuilt("heracross", ::HeracrossModel)
        inbuilt("salandit", ::SalanditModel)
        inbuilt("salazzle", ::SalazzleModel)
        inbuilt("jangmo-o", ::JangmoOModel)
        inbuilt("hakamo-o", ::HakamoOModel)
        inbuilt("kommo-o", ::KommoOModel)
        inbuilt("trapinch", ::TrapinchModel)
        inbuilt("vibrava", ::VibravaModel)
        inbuilt("flygon", ::FlygonModel)
        inbuilt("larvitar", ::LarvitarModel)
        inbuilt("pupitar", ::PupitarModel)
        inbuilt("tyranitar", ::TyranitarModel)
        inbuilt("impidimp", ::ImpidimpModel)
        inbuilt("morgrem", ::MorgremModel)
        inbuilt("grimmsnarl", ::GrimmsnarlModel)
        inbuilt("klefki", ::KlefkiModel)
        inbuilt("oshawott_hisui_bias", ::OshawottHisuiBiasModel)
        inbuilt("dewott_hisui_bias", ::DewottHisuiBiasModel)
        inbuilt("samurott_hisuian", ::SamurottHisuianModel)
        inbuilt("cyndaquil_hisui_bias", ::CyndaquilHisuiBiasModel)
        inbuilt("quilava_hisui_bias", ::QuilavaHisuiBiasModel)
        inbuilt("typhlosion_hisuian", ::TyphlosionHisuianModel)
        inbuilt("rowlet_hisui_bias", ::RowletHisuiBiasModel)
        inbuilt("dartrix_hisui_bias", ::DartrixHisuiBiasModel)
        inbuilt("decidueye_hisuian", ::DecidueyeHisuianModel)
    }

    override fun loadJsonPoser(json: String): (Bone) -> PokemonPoseableModel {
        // Faster to deserialize during asset load rather than rerunning this every time a poser is constructed.
        val jsonObject = JsonPokemonPoseableModel.gson.fromJson(json, JsonObject::class.java)
        return {
            JsonPokemonPoseableModel.JsonPokemonPoseableModelAdapter.modelPart = it
            JsonPokemonPoseableModel.gson.fromJson(jsonObject, JsonPokemonPoseableModel::class.java).also {
                it.poses.forEach { poseName, pose -> pose.poseName = poseName }
            }
        }
    }
}


