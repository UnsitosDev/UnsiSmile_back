SET NAMES utf8;

INSERT INTO `housings` VALUES ('1','Casa única en el terreno'),('2','Casa que comparte terreno con otra(s)'),('3','Casa dúplex'),('4','Departamento en edificio'),('5','Vivienda en vecindad o cuartería'),('6','Vivienda en cuarto de azotea de un edificio'),('7','Local no construido para habitación'),('8','Vivienda móvil'),('9','Refugio'),('10','Viviendas colectivas'),('99','No especificado de vivienda particular');

INSERT INTO `ethnic_groups` VALUES (1,'Náhuatl'),(2,'Maya'),(3,'Zapoteco'),(4,'Mixteco'),(5,'Otomí'),(6,'Totonaca'),(7,'Tzotzil'),(8,'Tzeltal'),(9,'Mazahua'),(10,'Mazateco'),(11,'Huasteco'),(12,'Chol'),(13,'Purépecha'),(14,'No especificada'),(15,'Chinanteco'),(16,'Mixe'),(17,'Tlapaneco'),(18,'Tarahumara'),(19,'Mayo'),(20,'Zoque'),(21,'Chontal de Tabasco'),(22,'Popoluca'),(23,'Chatino'),(24,'Amuzgo'),(25,'Tojolabal'),(26,'Huichol'),(27,'Tepehuan'),(28,'Triqui'),(29,'Popoloca'),(30,'Cora'),(31,'Mame'),(32,'Yaqui'),(33,'Cuicateco'),(34,'Huave'),(35,'Tepehua'),(36,'Chontal de Oaxaca'),(37,'Kanjobal'),(38,'Pame'),(39,'Chichimeca Jonaz'),(40,'Matlatzinca'),(41,'Guarijío'),(42,'Chuj'),(43,'Chocho'),(44,'Tacuate'),(45,'Ocuilteco'),(46,'Pima'),(47,'Jacalteco'),(48,'Kekchi'),(49,'Otras lenguas indígenas de América'),(50,'Lacandón'),(51,'Ixcateco'),(52,'Otras lenguas indígenas de México'),(53,'Seri'),(54,'Motocintleco'),(55,'Cakchiquel'),(56,'Quiche'),(57,'Paipai'),(58,'Papago'),(59,'Cucapá'),(60,'Kumiai'),(61,'Kikapú'),(62,'Cochimí'),(63,'Ixil'),(64,'Kiliwa'),(65,'Aguacateco');

INSERT INTO `facial_profile` VALUES (1,'Recto')(2,'Cóncavo'),(3,'Convexo');

INSERT INTO `genders` VALUES (1,'Masculino'),(2,'Femenino'),(99,'No binario');

INSERT INTO `hereditary_family_history_questions` VALUES (1,'Neoplasia (Cáncer)'),(2,'Diabetes'),(3,'Hipertensión Arterial'),(4,'Padecimientos mentales/neurológicos'),(5,'Obesidad'),(6,'Padecimientos hematológicos'),(7,'Malformaciones congénitas'),(8,'Problemas cardiacos');

INSERT INTO `marital_statuses` VALUES (1,'Soltero'),(2,'Casado'),(3,'Divorciado');

INSERT INTO `states` VALUES ('1','Aguascalientes'),('2','Baja California'),('3','Baja California Sur'),('4','Campeche'),('5','Chiapas'),('6','Chihuahua'),('7','Ciudad de México'),('8','Coahuila de Zaragoza'),('9','Colima'),('10','Durango'),('11','Guanajuato'),('12','Guerrero'),('13','Hidalgo'),('14','Jalisco'),('15','Edo. de México'),('16','Michoacán de Ocampo'),('17','Morelos'),('18','Nayarit'),('19','Nuevo León'),('20','Oaxaca'),('21','Puebla'),('22','Querétaro'),('23','Quintana Roo'),('24','San Luis Potosí'),('25','Sinaloa'),('26','Sonora'),('27','Tabasco'),('28','Tamaulipas'),('29','Tlaxcala'),('30','Veracruz de Ignacio de la Llave'),('31','Yucatán'),('32','Zacatecas'),('33','Nacional'),('34','Extranjero');

INSERT INTO `municipalities` VALUES ('2001','Abejones','20'),('2002','Acatlan de Perez Figueroa','20'),('2003','Animas Trujano','20'),('2004','Asuncion Cacalotepec','20'),('2005','Asuncion Cuyotepeji','20'),('2006','Asuncion Ixtaltepec','20'),('2007','Asuncion Nochixtlan','20'),('2008','Asuncion Ocotlan','20'),('2009','Asuncion Tlacolulita','20'),('2010','Ayoquezco de Aldama','20'),('2011','Ayotzintepec','20'),('2012','Calihuala','20'),('2013','Candelaria Loxicha','20'),('2014','Capulalpam de Mendez','20'),('2015','Chahuites','20'),('2016','Chalcatongo de Hidalgo','20'),('2017','Chiquihuitlan de Benito Juarez','20'),('2018','Cienega de Zimatlan','20'),('2019','Ciudad Ixtepec','20'),('2020','Coatecas Altas','20'),('2021','Coicoyan de las Flores','20'),('2022','Concepcion Buenavista','20'),('2023','Concepcion Papalo','20'),('2024','Constancia del Rosario','20'),('2025','Cosolapa','20'),('2026','Cosoltepec','20'),('2027','Cuilapam de Guerrero','20'),('2028','Cuna de la Independencia de Oaxaca','20'),('2029','Cuyamecalco Villa de Zaragoza','20'),('2030','El Barrio de la Soledad','20'),('2031','El Espinal','20'),('2032','Eloxochitlan de Flores Magon','20'),('2033','Fresnillo de Trujano','20'),('2034','Guadalupe Etla','20'),('2035','Guadalupe de Ramirez','20'),('2036','Guelatao de Juarez','20'),('2037','Guevea de Humboldt','20'),('2038','Heroica Ciudad de Ejutla de Crespo','20'),('2039','Heroica Ciudad de Huajuapan de Leon','20'),('2040','Heroica Ciudad de Juchitan de Zaragoza','20'),('2041','Heroica Ciudad de Tlaxiaco','20'),('2042','Heroica Villa Tezoatlan de Segura y Luna','20'),('2043','Huautepec','20'),('2044','Huautla de Jimenez','20'),('2045','Ixpantepec Nieves','20'),('2046','Ixtlan de Juarez','20'),('2047','La Compania','20'),('2048','La Pe','20'),('2049','La Reforma','20'),('2050','La Trinidad Vista Hermosa','20'),('2051','Loma Bonita','20'),('2052','Magdalena Apasco','20'),('2053','Magdalena Jaltepec','20'),('2054','Magdalena Mixtepec','20'),('2055','Magdalena Ocotlan','20'),('2056','Magdalena Penasco','20'),('2057','Magdalena Teitipac','20'),('2058','Magdalena Tequisistlan','20'),('2059','Magdalena Tlacotepec','20'),('2060','Magdalena Yodocono de Porfirio Diaz','20'),('2061','Magdalena Zahuatlan','20'),('2062','Mariscala de Juarez','20'),('2063','Martires de Tacubaya','20'),('2064','Matias Romero Avendano','20'),('2065','Mazatlan Villa de Flores','20'),('2066','Mesones Hidalgo','20'),('2067','Miahuatlan de Porfirio Diaz','20'),('2068','Mixistlan de la Reforma','20'),('2069','Monjas','20'),('2070','Natividad','20'),('2071','Nazareno Etla','20'),('2072','Nejapa de Madero','20'),('2073','Nuevo Zoquiapam','20'),('2074','Oaxaca de Juarez','20'),('2075','Ocotlan de Morelos','20'),('2076','Pinotepa de Don Luis','20'),('2077','Pluma Hidalgo','20'),('2078','Putla Villa de Guerrero','20'),('2079','Reforma de Pineda','20'),('2080','Reyes Etla','20'),('2081','Rojas de Cuauhtemoc','20'),('2082','Salina Cruz','20'),('2083','San Agustin Amatengo','20'),('2084','San Agustin Atenango','20'),('2085','San Agustin Chayuco','20'),('2086','San Agustin Etla','20'),('2087','San Agustin Loxicha','20'),('2088','San Agustin Tlacotepec','20'),('2089','San Agustin Yatareni','20'),('2090','San Agustin de las Juntas','20'),('2091','San Andres Cabecera Nueva','20'),('2092','San Andres Dinicuiti','20'),('2093','San Andres Huaxpaltepec','20'),('2094','San Andres Huayapam','20'),('2095','San Andres Ixtlahuaca','20'),('2096','San Andres Lagunas','20'),('2097','San Andres Nuxino','20'),('2098','San Andres Paxtlan','20'),('2099','San Andres Sinaxtla','20'),('2100','San Andres Solaga','20'),('2101','San Andres Teotilalpam','20'),('2102','San Andres Tepetlapa','20'),('2103','San Andres Yaa','20'),('2104','San Andres Zabache','20'),('2105','San Andres Zautla','20'),('2106','San Antonino Castillo Velasco','20'),('2107','San Antonino Monte Verde','20'),('2108','San Antonino el Alto','20'),('2109','San Antonio Acutla','20'),('2110','San Antonio Huitepec','20'),('2111','San Antonio Nanahuatipam','20'),('2112','San Antonio Sinicahua','20'),('2113','San Antonio Tepetlapa','20'),('2114','San Antonio de la Cal','20'),('2115','San Baltazar Chichicapam','20'),('2116','San Baltazar Loxicha','20'),('2117','San Baltazar Yatzachi el Bajo','20'),('2118','San Bartolo Coyotepec','20'),('2119','San Bartolo Soyaltepec','20'),('2120','San Bartolo Yautepec','20'),('2121','San Bartolome Ayautla','20'),('2122','San Bartolome Loxicha','20'),('2123','San Bartolome Quialana','20'),('2124','San Bartolome Yucuane','20'),('2125','San Bartolome Zoogocho','20'),('2126','San Bernardo Mixtepec','20'),('2127','San Blas Atempa','20'),('2128','San Carlos Yautepec','20'),('2129','San Cristobal Amatlan','20'),('2130','San Cristobal Amoltepec','20'),('2131','San Cristobal Lachirioag','20'),('2132','San Cristobal Suchixtlahuaca','20'),('2133','San Dionisio Ocotepec','20'),('2134','San Dionisio Ocotlan','20'),('2135','San Dionisio del Mar','20'),('2136','San Esteban Atatlahuca','20'),('2137','San Felipe Jalapa de Diaz','20'),('2138','San Felipe Tejalapam','20'),('2139','San Felipe Usila','20'),('2140','San Francisco Cahuacua','20'),('2141','San Francisco Cajonos','20'),('2142','San Francisco Chapulapa','20'),('2143','San Francisco Chindua','20'),('2144','San Francisco Huehuetlan','20'),('2145','San Francisco Ixhuatan','20'),('2146','San Francisco Jaltepetongo','20'),('2147','San Francisco Lachigolo','20'),('2148','San Francisco Logueche','20'),('2149','San Francisco Nuxano','20'),('2150','San Francisco Ozolotepec','20'),('2151','San Francisco Sola','20'),('2152','San Francisco Telixtlahuaca','20'),('2153','San Francisco Teopan','20'),('2154','San Francisco Tlapancingo','20'),('2155','San Francisco del Mar','20'),('2156','San Gabriel Mixtepec','20'),('2157','San Ildefonso Amatlan','20'),('2158','San Ildefonso Sola','20'),('2159','San Ildefonso Villa Alta','20'),('2160','San Jacinto Amilpas','20'),('2161','San Jacinto Tlacotepec','20'),('2162','San Jeronimo Coatlan','20'),('2163','San Jeronimo Silacayoapilla','20'),('2164','San Jeronimo Sosola','20'),('2165','San Jeronimo Taviche','20'),('2166','San Jeronimo Tecoatl','20'),('2167','San Jeronimo Tlacochahuaya','20'),('2168','San Jorge Nuchita','20'),('2169','San Jose Ayuquila','20'),('2170','San Jose Chiltepec','20'),('2171','San Jose Estancia Grande','20'),('2172','San Jose Independencia','20'),('2173','San Jose Lachiguiri','20'),('2174','San Jose Tenango','20'),('2175','San Jose del Penasco','20'),('2176','San Jose del Progreso','20'),('2177','San Juan Achiutla','20'),('2178','San Juan Atepec','20'),('2179','San Juan Bautista Atatlahuca','20'),('2180','San Juan Bautista Coixtlahuaca','20'),('2181','San Juan Bautista Cuicatlan','20'),('2182','San Juan Bautista Guelache','20'),('2183','San Juan Bautista Jayacatlan','20'),('2184','San Juan Bautista Lo de Soto','20'),('2185','San Juan Bautista Suchitepec','20'),('2186','San Juan Bautista Tlachichilco','20'),('2187','San Juan Bautista Tlacoatzintepec','20'),('2188','San Juan Bautista Tuxtepec','20'),('2189','San Juan Bautista Valle Nacional','20'),('2190','San Juan Cacahuatepec','20'),('2191','San Juan Chicomezuchil','20'),('2192','San Juan Chilateca','20'),('2193','San Juan Cieneguilla','20'),('2194','San Juan Coatzospam','20'),('2195','San Juan Colorado','20'),('2196','San Juan Comaltepec','20'),('2197','San Juan Cotzocon','20'),('2198','San Juan Diuxi','20'),('2199','San Juan Evangelista Analco','20'),('2200','San Juan Guelavia','20'),('2201','San Juan Guichicovi','20'),('2202','San Juan Ihualtepec','20'),('2203','San Juan Juquila Mixes','20'),('2204','San Juan Juquila Vijanos','20'),('2205','San Juan Lachao','20'),('2206','San Juan Lachigalla','20'),('2207','San Juan Lajarcia','20'),('2208','San Juan Lalana','20'),('2209','San Juan Mazatlan','20'),('2210','San Juan Mixtepec','20'),('2211','San Juan Mixtepec','20'),('2212','San Juan Numi','20'),('2213','San Juan Ozolotepec','20'),('2214','San Juan Petlapa','20'),('2215','San Martin Huamelulpam','20'),('2216','San Martin Itunyoso','20'),('2217','San Martin Lachila','20'),('2218','San Martin Peras','20'),('2219','San Martin Tilcajete','20'),('2220','San Martin Toxpalan','20'),('2221','San Martin Zacatepec','20'),('2222','San Martin de los Cansecos','20'),('2223','San Mateo Cajonos','20'),('2224','San Mateo Etlatongo','20'),('2225','San Mateo Nejapam','20'),('2226','San Mateo Penasco','20'),('2227','San Mateo Pinas','20'),('2228','San Mateo Rio Hondo','20'),('2229','San Mateo Sindihui','20'),('2230','San Mateo Tlapiltepec','20'),('2231','San Mateo Yoloxochitlan','20'),('2232','San Mateo Yucutindoo','20'),('2233','San Mateo del Mar','20'),('2234','San Melchor Betaza','20'),('2235','San Miguel Achiutla','20'),('2236','San Miguel Ahuehuetitlan','20'),('2237','San Miguel Aloapam','20'),('2238','San Miguel Amatitlan','20'),('2239','San Miguel Amatlan','20'),('2240','San Miguel Chicahua','20'),('2241','San Miguel Chimalapa','20'),('2242','San Miguel Coatlan','20'),('2243','San Miguel Ejutla','20'),('2244','San Miguel Huautla','20'),('2245','San Miguel Mixtepec','20'),('2246','San Miguel Panixtlahuaca','20'),('2247','San Miguel Peras','20'),('2248','San Miguel Piedras','20'),('2249','San Miguel Quetzaltepec','20'),('2250','San Miguel Santa Flor','20'),('2251','San Miguel Soyaltepec','20'),('2252','San Miguel Suchixtepec','20'),('2253','San Miguel Tecomatlan','20'),('2254','San Miguel Tenango','20'),('2255','San Miguel Tequixtepec','20'),('2256','San Miguel Tilquiapam','20'),('2257','San Miguel Tlacamama','20'),('2258','San Miguel Tlacotepec','20'),('2259','San Miguel Tulancingo','20'),('2260','San Miguel Yotao','20'),('2261','San Miguel del Puerto','20'),('2262','San Miguel del Rio','20'),('2263','San Miguel el Grande','20'),('2264','San Nicolas Hidalgo','20'),('2265','San Nicolas','20'),('2266','San Pablo Coatlan','20'),('2267','San Pablo Cuatro Venados','20'),('2268','San Pablo Etla','20'),('2269','San Pablo Huitzo','20'),('2270','San Pablo Huixtepec','20'),('2271','San Pablo Macuiltianguis','20'),('2272','San Pablo Tijaltepec','20'),('2273','San Pablo Villa de Mitla','20'),('2274','San Pablo Yaganiza','20'),('2275','San Pedro Amuzgos','20'),('2276','San Pedro Apostol','20'),('2277','San Pedro Atoyac','20'),('2278','San Pedro Cajonos','20'),('2279','San Pedro Comitancillo','20'),('2280','San Pedro Coxcaltepec Cantaros','20'),('2281','San Pedro Huamelula','20'),('2282','San Pedro Huilotepec','20'),('2283','San Pedro Ixcatlan','20'),('2284','San Pedro Ixtlahuaca','20'),('2285','San Pedro Jaltepetongo','20'),('2286','San Pedro Jicayan','20'),('2287','San Pedro Jocotipac','20'),('2288','San Pedro Juchatengo','20'),('2289','San Pedro Martir Quiechapa','20'),('2290','San Pedro Martir Yucuxaco','20'),('2291','San Pedro Martir','20'),('2292','San Pedro Mixtepec','20'),('2293','San Pedro Mixtepec','20'),('2294','San Pedro Molinos','20'),('2295','San Pedro Nopala','20'),('2296','San Pedro Ocopetatillo','20'),('2297','San Pedro Ocotepec','20'),('2298','San Pedro Pochutla','20'),('2299','San Pedro Quiatoni','20'),('2300','San Pedro Sochiapam','20'),('2301','San Pedro Tapanatepec','20'),('2302','San Pedro Taviche','20'),('2303','San Pedro Teozacoalco','20'),('2304','San Pedro Teutila','20'),('2305','San Pedro Tidaa','20'),('2306','San Pedro Topiltepec','20'),('2307','San Pedro Totolapam','20'),('2308','San Pedro Yaneri','20'),('2309','San Pedro Yolox','20'),('2310','San Pedro Yucunama','20'),('2311','San Pedro el Alto','20'),('2312','San Pedro y San Pablo Ayutla','20'),('2313','San Pedro y San Pablo Teposcolula','20'),('2314','San Pedro y San Pablo Tequixtepec','20'),('2315','San Raymundo Jalpan','20'),('2316','San Sebastian Abasolo','20'),('2317','San Sebastian Coatlan','20'),('2318','San Sebastian Ixcapa','20'),('2319','San Sebastian Nicananduta','20'),('2320','San Sebastian Rio Hondo','20'),('2321','San Sebastian Tecomaxtlahuaca','20'),('2322','San Sebastian Teitipac','20'),('2323','San Sebastian Tutla','20'),('2324','San Simon Almolongas','20'),('2325','San Simon Zahuatlan','20'),('2326','San Vicente Coatlan','20'),('2327','San Vicente Lachixio','20'),('2328','San Vicente Nunu','20'),('2329','Santa Ana Ateixtlahuaca','20'),('2330','Santa Ana Cuauhtemoc','20'),('2331','Santa Ana Tavela','20'),('2332','Santa Ana Tlapacoyan','20'),('2333','Santa Ana Yareni','20'),('2334','Santa Ana Zegache','20'),('2335','Santa Ana del Valle','20'),('2336','Santa Ana','20'),('2337','Santa Catalina Quieri','20'),('2338','Santa Catarina Cuixtla','20'),('2339','Santa Catarina Ixtepeji','20'),('2340','Santa Catarina Juquila','20'),('2341','Santa Catarina Lachatao','20'),('2342','Santa Catarina Loxicha','20'),('2343','Santa Maria Tataltepec','20'),('2344','Santa Maria Tecomavaca','20'),('2345','Santa Maria Temaxcalapa','20'),('2346','Santa Maria Temaxcaltepec','20'),('2347','Santa Maria Teopoxco','20'),('2348','Santa Maria Tepantlali','20'),('2349','Santa Maria Texcatitlan','20'),('2350','Santa Maria Tlahuitoltepec','20'),('2351','Santa Maria Tlalixtac','20'),('2352','Santa Maria Tonameca','20'),('2353','Santa Maria Totolapilla','20'),('2354','Santa Maria Xadani','20'),('2355','Santa Maria Yalina','20'),('2356','Santa Maria Yavesia','20'),('2357','Santa Maria Yolotepec','20'),('2358','Santa Maria Yosoyua','20'),('2359','Santa Maria Yucuhiti','20'),('2360','Santa Maria Zacatepec','20'),('2361','Santa Maria Zaniza','20'),('2362','Santa Maria Zoquitlan','20'),('2363','Santa Maria del Rosario','20'),('2364','Santa Maria del Tule','20'),('2365','Santa Maria la Asuncion','20'),('2366','Santiago Amoltepec','20'),('2367','Santiago Apoala','20'),('2368','Santiago Apostol','20'),('2369','Santiago Astata','20'),('2370','Santiago Atitlan','20'),('2371','Santiago Ayuquililla','20'),('2372','Santiago Cacaloxtepec','20'),('2373','Santiago Camotlan','20'),('2374','Santiago Chazumba','20'),('2375','Santiago Choapam','20'),('2376','Santiago Comaltepec','20'),('2377','Santiago Huajolotitlan','20'),('2378','Santiago Huauclilla','20'),('2379','Santiago Ihuitlan Plumas','20'),('2380','Santiago Ixcuintepec','20'),('2381','Santiago Ixtayutla','20'),('2382','Santiago Jamiltepec','20'),('2383','Santiago Jocotepec','20'),('2384','Santiago Juxtlahuaca','20'),('2385','Santiago Lachiguiri','20'),('2386','Santiago Lalopa','20'),('2387','Santiago Laollaga','20'),('2388','Santiago Laxopa','20'),('2389','Santiago Llano Grande','20'),('2390','Santiago Matatlan','20'),('2391','Santiago Miltepec','20'),('2392','Santiago Minas','20'),('2393','Santiago Nacaltepec','20'),('2394','Santiago Nejapilla','20'),('2395','Santiago Niltepec','20'),('2396','Santiago Nundiche','20'),('2397','Santiago Nuyoo','20'),('2398','Santiago Pinotepa Nacional','20'),('2399','Santiago Suchilquitongo','20'),('2400','Santiago Tamazola','20'),('2401','Santiago Tapextla','20'),('2402','Santiago Tenango','20'),('2403','Santiago Tepetlapa','20'),('2404','Santiago Tetepec','20'),('2405','Santiago Texcalcingo','20'),('2406','Santiago Textitlan','20'),('2407','Santiago Tilantongo','20'),('2408','Santiago Tillo','20'),('2409','Santiago Tlazoyaltepec','20'),('2410','Santiago Xanica','20'),('2411','Santiago Xiacui','20'),('2412','Santiago Yaitepec','20'),('2413','Santiago Yaveo','20'),('2414','Santiago Yolomecatl','20'),('2415','Santiago Yosondua','20'),('2416','Santiago Yucuyachi','20'),('2417','Santiago Zacatepec','20'),('2418','Santiago Zoochila','20'),('2419','Santiago del Rio','20'),('2420','Santo Domingo Albarradas','20'),('2421','Santo Domingo Armenta','20'),('2422','Santo Domingo Chihuitan','20'),('2423','Santo Domingo Ingenio','20'),('2424','Santo Domingo Ixcatlan','20'),('2425','Santo Domingo Nuxaa','20'),('2426','Santo Domingo Ozolotepec','20'),('2427','Santo Domingo Petapa','20'),('2428','Santo Domingo Roayaga','20'),('2429','Santo Domingo Tehuantepec','20'),('2430','Santo Domingo Teojomulco','20'),('2431','Santo Domingo Tepuxtepec','20'),('2432','Santo Domingo Tlatayapam','20'),('2433','Santo Domingo Tomaltepec','20'),('2434','Santo Domingo Tonala','20'),('2435','Santo Domingo Tonaltepec','20'),('2436','Santo Domingo Xagacia','20'),('2437','Santo Domingo Yanhuitlan','20'),('2438','Santo Domingo Yodohino','20'),('2439','Santo Domingo Zanatepec','20'),('2441','Santo Domingo de Morelos','20'),('2442','Santo Tomas Jalieza','20'),('2443','Santo Tomas Mazaltepec','20'),('2444','Santo Tomas Ocotepec','20'),('2445','Santo Tomas Tamazulapan','20'),('2446','Santos Reyes Nopala','20'),('2447','Santos Reyes Papalo','20'),('2448','Santos Reyes Tepejillo','20'),('2449','Santos Reyes Yucuna','20'),('2450','Silacayoapam','20'),('2451','Sitio de Xitlapehua','20'),('2452','Soledad Etla','20'),('2453','Tamazulapam del Espiritu Santo','20'),('2454','Tanetze de Zaragoza','20'),('2455','Taniche','20'),('2456','Tataltepec de Valdes','20'),('2457','Teococuilco de Marcos Perez','20'),('2458','Teotitlan de Flores Magon','20'),('2459','Teotitlan del Valle','20'),('2460','Teotongo','20'),('2461','Tepelmeme Villa de Morelos','20'),('2462','Tlacolula de Matamoros','20'),('2463','Tlacotepec Plumas','20'),('2464','Tlalixtac de Cabrera','20'),('2465','Totontepec Villa de Morelos','20'),('2466','Trinidad Zaachila','20'),('2467','Union Hidalgo','20'),('2468','Valerio Trujano','20'),('2469','Villa Diaz Ordaz','20'),('2470','Villa Hidalgo','20'),('2471','Villa Sola de Vega','20'),('2472','Villa Talea de Castro','20'),('2473','Villa Tejupam de la Union','20'),('2474','Villa de Chilapa de Diaz','20'),('2475','Villa de Etla','20'),('2476','Villa de Tamazulapam del Progreso','20'),('2477','Villa de Tututepec','20'),('2478','Villa de Zaachila','20'),('2479','Yaxe','20'),('2480','Yogana','20'),('2481','Yutanduchi de Guerrero','20'),('2482','Zapotitlan Lagunas','20'),('2483','Zapotitlan Palmas','20'),('2484','Zimatlan de Alvarez','20');

INSERT INTO `localities` VALUES ('01010','Miahuatlan','70805','2067');

INSERT INTO `nationalities` VALUES (1,'Mexicana'),(2,'Estadounidense'),(3,'Canadiense'),(4,'Española'),(5,'Francesa');

INSERT INTO `neighborhoods` VALUES (1,'Colonia centro','01010'),(2,'La merced','01010'),(3,'Barrio San Isidro','01010');

INSERT INTO `streets` VALUES (1,'Calle Juárez',1),(2,'Avenida Reforma',2),(3,'Calle Madero',3);

INSERT INTO `addresses` VALUES (1,'10','10','1',3),(2,'st','st','1',1);

INSERT INTO `occupations` VALUES (1,'Médico'),(2,'Maestro'),(3,'Ingeniero'),(11,'Contador'),(12,'Psicólogo'),(13,'Periodista'),(14,'Electricista'),(15,'Mecánico'),(16,'Economista'),(17,'Administrador'),(18,'Científico'),(19,'Farmacéutico'),(20,'Veterinario'),(99,'No definido');

INSERT INTO `open_questions_pathological_antecedents` VALUES (1,'¿Has sido hospitalizado? (en mujeres también anotar datos de parto)'),(2,'Motivo de la hospitalización'),(3,'¿Ha tomado algún medicamento recientemente?'),(4,'¿Cuál es el motivo?'),(5,'¿Ha tenido algún problema con la anestesia dental o anestesia general?'),(6,'¿Cuál es el problema?'),(7,'¿Es alérgico/a a algún medicamento o sustancia?'),(8,'¿Cuál es la sustancia a la que es alérgico/a?'),(9,'(Solo para mujeres) ¿Está embarazada?'),(10,'Meses de embarazo');

INSERT INTO people VALUES ('AOPS011028HOCNRLA9','2003-06-04','example@gmail.com','ANTONIO','JUAN','5645123541','Martíez','LUIS',1),('FIMJ011004HOCGRLA8','2001-10-04','froste@gmail.com','Figueroa','Joel','5018221525','Martinez','Francisco',1);

INSERT INTO `regions_measurement_pockets` VALUES (1,'VESTIBULARES SUPERIORES'),(2,'PALATINAS'),(3,'VESTIBULARES INFERIORES'),(4,'LINGUALES');

INSERT INTO `religions` VALUES (133100,'Adventistas del Séptimo Día'),(310301,'Agnósticos'),(240102,'Ágora'),(110104,'Agustino'),(131100,'Amistad Cristiana'),(110105,'Amor Misericordioso'),(130100,'Anabautista/Menonita'),(290101,'Ananda Marga'),(130200,'Anglicana/Episcopal'),(240103,'Antroposofía'),(280102,'Apostólica Tradicional México-USA (Santa Muerte)'),(131200,'Asambleas de Dios'),(110106,'Asuncionista'),(310201,'Ateos'),(230301,'Bahaísmo'),(130300,'Bautista'),(110107,'Benedictino'),(230101,'Budismo'),(130701,'Calvinista'),(110108,'Capuchino'),(110109,'Carmelita'),(110110,'Carmelita Descalza'),(131801,'Casa de Oración'),(110103,'Católico'),(110101,'Católico Apostólico Romano'),(110102,'Católico Romano'),(131802,'Centro de Fe, Esperanza y Amor de la Cruzada Misionera de Avivamiento'),(131803,'Centro de Fe, Esperanza y Amor Fuerza Ágape'),(230302,'Centro Onkaranada'),(240104,'Centros de Tensegridad'),(250101,'Chamanismo'),(220102,'Chiíes'),(290102,'Ciencia Cristiana'),(240114,'Ciencia Esotérica'),(290103,'Cienciología o Dianética'),(110111,'Cisterciense'),(110112,'Claretiano'),(110113,'Clérigos San Viator'),(110114,'Comboniano'),(240105,'Comunidad del Arco iris'),(110115,'Comunidad Salesiana'),(110116,'Concepcionistas Franciscanos'),(230303,'Confusionismo'),(110118,'Congregación de los Padres Bayonenses'),(130702,'Congregacional'),(290104,'Contacto Angélico'),(110117,'Corazonista'),(290105,'Creciendo en Gracia'),(270106,'Cristiana Espiritual'),(134100,'Cristianas'),(120106,'Cristianos Tradicionalistas'),(130703,'Cuáquera'),(240106,'Cuarto Camino'),(280103,'Culto a Jesús Malverde'),(110119,'Diócesis Maronita de México, Eparquía Nuestra Señora de los Mártires del Líbano'),(131804,'Discípulos de Cristo'),(110120,'Dominico'),(290106,'Eckankar'),(131805,'Ejército de Salvación'),(250102,'El Costumbre'),(110121,'Eparquía Greco-Melquita Católica'),(110122,'Ermitaño Eucarístico del Padre celestial'),(110123,'Esclavas de María Inmaculada'),(110124,'Escolapio'),(240107,'Esenios'),(270103,'Espiritista'),(270113,'Espíritu Santo, Pureza, Amor y Luz'),(240108,'Espiritualidad Nueva Era'),(270102,'Espiritualista'),(270105,'Espiritualista de la Tercera Era'),(270108,'Espiritualista Fe, Esperanza y Caridad, Jesús de la Montaña'),(270109,'Espiritualista para el Divino Maestro y la Pureza de María'),(270101,'Espiritualistas Eliasistas'),(270104,'Espiritualistas Trinitarios Marianos'),(110125,'Eudista'),(134200,'Evangélicas'),(110126,'Franciscano'),(131806,'Fraternidad Pentecostés Independiente'),(110127,'Fraternidad Sacerdotal San Pío X (Lefebristas)'),(240115,'Gnosticismo'),(240109,'Gran Fraternidad Blanca'),(240110,'Gran Fraternidad Universal'),(110128,'Guadalupano'),(230202,'Hare Krishna'),(110129,'Hermandad de Sacerdotes Operarios Diocesanos'),(110130,'Hermano de Nuestra Señora de Consolación en el Mundo'),(110131,'Hermano de San Juan de Dios'),(110133,'Hermano Obrero de María'),(110132,'Hermanos Fossores de la Sagrada Misericordia'),(110134,'Hijas de la Caridad de San Vicente de Paúl'),(110135,'Hijas de la Inmaculada Concepción'),(110136,'Hijas de María Auxiliadora'),(110137,'Hijas de San Pablo'),(110138,'Hijas del Divino Salvador'),(110139,'Hijas del Espíritu Santo'),(110140,'Hijos de la Sagrada Familia'),(110141,'Hijos de María Madre de la Iglesia'),(230201,'Hinduismo'),(110142,'Hospitalario de San Juan de Dios'),(250103,'Huaxcam Dios Xastacná'),(131807,'Iglesia Agua Viva'),(131808,'Iglesia Aposento Alto'),(131300,'Iglesia Apostólica de la Fe en Cristo Jesús'),(132601,'Iglesia Bíblica'),(110143,'Iglesia Católica Apostólica Mexicana'),(120102,'Iglesia Católica Apostólica Ortodoxa'),(120103,'Iglesia Católica Apostólica Ortodoxa del Patriarca de Moscú'),(120107,'Iglesia Católica Apostólica Romana Reformada'),(120108,'Iglesia Católica de Cristo Internacional'),(120109,'Iglesia Católica de los Apóstoles de los Tiempos Posteriores'),(110144,'Iglesia Católica de Rito Latino'),(120104,'Iglesia Católica Griega'),(120110,'Iglesia Católica Nacional Mexicana'),(120111,'Iglesia Católica Tridentina Rito Latino'),(131809,'Iglesia Cristiana Espiritual'),(131810,'Iglesia Cristiana Evangélica Pentecostal'),(131811,'Iglesia Cristiana Independiente Pentecostés'),(132100,'Iglesia Cristiana Interdenominacional'),(131812,'Iglesia Cristiana Interdenominacional de la República Mexicana'),(131819,'Iglesia Cristiana Solo Cristo Salva'),(132300,'Iglesia de Cristo'),(132602,'Iglesia de Cristo Miel'),(131400,'Iglesia de Dios'),(131500,'Iglesia de Dios de la Profecía'),(131600,'Iglesia de Dios en México del Evangelio Completo'),(133200,'Iglesia de Jesucristo de los Santos de los Últimos Días (Mormones)'),(131813,'Iglesia de Jesucristo sobre la Roca'),(290107,'Iglesia de la Unificación'),(131814,'Iglesia del Concilio Evangélico Pentecostal'),(132200,'Iglesia del Dios Vivo, Columna y Apoyo de la Verdad, la Luz del Mundo'),(132400,'Iglesia del Nazareno'),(132603,'Iglesia Evangélica Independiente de la República Mexicana'),(131815,'Iglesia Evangélica Pentecostés Cristo Roca de mi Salvación'),(280101,'Iglesia Fidencista Cristiana'),(110145,'Iglesia Madre'),(131816,'Iglesia Mexicana del Evangelio de Cristo Pentecostés'),(270112,'Iglesia Mexicana La Mujer Hermosa Vestida de Sol'),(120105,'Iglesia Ortodoxa Católica'),(131817,'Iglesia Pentecostal Unida de México'),(132604,'Iglesia Renovada de Jesucristo y los Apóstoles del Amor Divino'),(131818,'Iglesia Universal del Reino de Dios'),(250104,'In kantonal, Casa del Sol'),(240111,'Instituto Arica'),(110146,'Instituto del Verbo Encarnado'),(220101,'Islámicas'),(230304,'Jainismo'),(220103,'Jariyismo'),(110147,'Jerónimo'),(110148,'Jesuita'),(210101,'Judaica'),(270111,'La Luz y Esperanza'),(270110,'Las Tres Potencias Padre, Hijo y Espíritu Santo'),(110149,'Lazarita'),(110150,'Legionarios de Cristo'),(290108,'Loordista'),(130400,'Luterana'),(110151,'Mantellata Sierva de María'),(110153,'Marista'),(240116,'Masonería'),(290109,'Meditación Trascendental'),(110152,'Mercedaria del Santísimo Sacramento'),(240117,'Metafísica'),(240112,'Metafísicos Cristianos'),(130500,'Metodista'),(250105,'Mexicáyotl'),(290110,'Misión Rama'),(110154,'Misionera de la Cruz'),(110155,'Misionera del Verbo Divino'),(110156,'Misionera Hija del Corazón de María'),(110157,'Misionero de Guadalupe'),(110158,'Misionero de la Inmaculada Concepción'),(110159,'Misionero del Espíritu Santo'),(110160,'Misionero Redentorista'),(110161,'Misionero Xaveriano en el Mundo'),(110162,'Monje de la Adoración'),(250106,'Movimiento Confederado Restaurador de la Cultura de Anáhuac'),(290111,'Movimiento del Sendero Interior del Alma (MSIA)'),(131820,'Movimiento Iglesia Evangélica Pentecostés Independiente'),(290112,'Movimiento Raeliano'),(132500,'Movimientos Sincréticos Judaicos Neoisraelitas'),(240101,'New Age (Nueva era)'),(310101,'Ninguna religión'),(290113,'Niños de Dios'),(110163,'Nuestra Señora de la Consolación'),(240113,'Nueva Acrópolis'),(280104,'Nueva Jerusalén'),(110164,'Obra de Don Orione'),(240118,'Ocultismo'),(110165,'Operario del Reino de Cristo'),(110166,'Orden de San Benito'),(260101,'Origen afro'),(120101,'Ortodoxo'),(131824,'Otras asociaciones pentecostales'),(132605,'Otras cristianas evangélicas'),(130704,'Otras protestantes'),(290119,'Otros nuevos movimientos religiosos'),(290114,'Pacal Votán'),(290115,'Palmar de Troya'),(110167,'Pasionista'),(110168,'Paulino'),(134300,'Pentecostales'),(110169,'Pequeña Obra de la Divina Providencia'),(130600,'Presbiteriana'),(131700,'Príncipe de Paz'),(134400,'Protestantes'),(260102,'Rastafaris'),(250107,'Regina'),(999999,'Religión no especificada'),(250109,'Religiones indígenas'),(110170,'Religiosa de la Asunción'),(110171,'Religiosa de Nuestra Señora de Sión'),(110172,'Religiosa de San José de Gerona'),(110173,'Religiosa del Sagrado Corazón'),(240119,'Rosacruces'),(110174,'Sagrado Corazón'),(110175,'Salesiano'),(110176,'San José de Tarbes'),(280105,'San Pascualito'),(290116,'Santa Iglesia Tao Cristiana Espiritual'),(110177,'Servidor del Señor y la Virgen'),(110178,'Sierva de Jesús'),(110179,'Sierva de Jesús de la Caridad'),(110180,'Sierva de la Pasión'),(110181,'Siervo de María'),(110182,'Siervos de la Sagrada Familia'),(230305,'Sijismo'),(310401,'Sin adscripción religiosa (creyentes)'),(230306,'Sintoísmo'),(290117,'Sociedad de Ascensión(Ishayas)'),(110183,'Sociedad de San Pablo'),(110184,'Sociedad de San Pío X'),(270107,'Sociedad Judictora Reinado de Leonardo Alcalá Leos'),(120112,'Sociedad Sacerdotal Trento'),(290118,'Sri Sathya Sai Baba'),(220105,'Sufismo'),(220104,'Suníes'),(230307,'Taoísmo'),(240120,'Teosofía'),(110185,'Terciaria Capuchina de la Sagrada Familia'),(133300,'Testigos de Jehová'),(250108,'Toltecáyotl'),(120113,'Unión Católica Trento Mexicana'),(131822,'Unión de Iglesias Evangélicas Independientes'),(131821,'Unión Nacional de Iglesias Cristianas Evangélicas (UNICE)'),(131823,'Unión Nacional de Iglesias Evangélicas Pentecostés Emmanuel'),(110186,'Ursulina de la Unión Romana'),(110187,'Vicentino'),(240121,'Wicca'),(230308,'Zoroastrismo');

INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_STUDENT');