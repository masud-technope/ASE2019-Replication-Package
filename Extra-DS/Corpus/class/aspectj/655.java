import org.aspectj.testing.Tester;

public class CaseClauses {

    public static void main(String[] args) {
        new CaseClauses().realMain(args);
    }

    public void realMain(String[] args) {
        int SIZE = 0377;
        boolean[] bs = new boolean[SIZE];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = false;
        }
        for (int i = 0; i < SIZE; i++) {
            switch((char) i) {
                case '\0':
                    bs[0] = true;
                    break;
                case '\1':
                    bs[1] = true;
                    break;
                case '\2':
                    bs[2] = true;
                    break;
                case '\3':
                    bs[3] = true;
                    break;
                case '\4':
                    bs[4] = true;
                    break;
                case '\5':
                    bs[5] = true;
                    break;
                case '\6':
                    bs[6] = true;
                    break;
                case '\7':
                    bs[7] = true;
                    break;
                case '\10':
                    bs[8] = true;
                    break;
                case '\11':
                    bs[9] = true;
                    break;
                case '\12':
                    bs[10] = true;
                    break;
                case '\13':
                    bs[11] = true;
                    break;
                case '\14':
                    bs[12] = true;
                    break;
                case '\15':
                    bs[13] = true;
                    break;
                case '\16':
                    bs[14] = true;
                    break;
                case '\17':
                    bs[15] = true;
                    break;
                case '\20':
                    bs[16] = true;
                    break;
                case '\21':
                    bs[17] = true;
                    break;
                case '\22':
                    bs[18] = true;
                    break;
                case '\23':
                    bs[19] = true;
                    break;
                case '\24':
                    bs[20] = true;
                    break;
                case '\25':
                    bs[21] = true;
                    break;
                case '\26':
                    bs[22] = true;
                    break;
                case '\27':
                    bs[23] = true;
                    break;
                case '\30':
                    bs[24] = true;
                    break;
                case '\31':
                    bs[25] = true;
                    break;
                case '\32':
                    bs[26] = true;
                    break;
                case '\33':
                    bs[27] = true;
                    break;
                case '\34':
                    bs[28] = true;
                    break;
                case '\35':
                    bs[29] = true;
                    break;
                case '\36':
                    bs[30] = true;
                    break;
                case '\37':
                    bs[31] = true;
                    break;
                case '\40':
                    bs[32] = true;
                    break;
                case '\41':
                    bs[33] = true;
                    break;
                case '\42':
                    bs[34] = true;
                    break;
                case '\43':
                    bs[35] = true;
                    break;
                case '\44':
                    bs[36] = true;
                    break;
                case '\45':
                    bs[37] = true;
                    break;
                case '\46':
                    bs[38] = true;
                    break;
                case '\47':
                    bs[39] = true;
                    break;
                case '\50':
                    bs[40] = true;
                    break;
                case '\51':
                    bs[41] = true;
                    break;
                case '\52':
                    bs[42] = true;
                    break;
                case '\53':
                    bs[43] = true;
                    break;
                case '\54':
                    bs[44] = true;
                    break;
                case '\55':
                    bs[45] = true;
                    break;
                case '\56':
                    bs[46] = true;
                    break;
                case '\57':
                    bs[47] = true;
                    break;
                case '\60':
                    bs[48] = true;
                    break;
                case '\61':
                    bs[49] = true;
                    break;
                case '\62':
                    bs[50] = true;
                    break;
                case '\63':
                    bs[51] = true;
                    break;
                case '\64':
                    bs[52] = true;
                    break;
                case '\65':
                    bs[53] = true;
                    break;
                case '\66':
                    bs[54] = true;
                    break;
                case '\67':
                    bs[55] = true;
                    break;
                case '\70':
                    bs[56] = true;
                    break;
                case '\71':
                    bs[57] = true;
                    break;
                case '\72':
                    bs[58] = true;
                    break;
                case '\73':
                    bs[59] = true;
                    break;
                case '\74':
                    bs[60] = true;
                    break;
                case '\75':
                    bs[61] = true;
                    break;
                case '\76':
                    bs[62] = true;
                    break;
                case '\77':
                    bs[63] = true;
                    break;
                case '\100':
                    bs[64] = true;
                    break;
                case '\101':
                    bs[65] = true;
                    break;
                case '\102':
                    bs[66] = true;
                    break;
                case '\103':
                    bs[67] = true;
                    break;
                case '\104':
                    bs[68] = true;
                    break;
                case '\105':
                    bs[69] = true;
                    break;
                case '\106':
                    bs[70] = true;
                    break;
                case '\107':
                    bs[71] = true;
                    break;
                case '\110':
                    bs[72] = true;
                    break;
                case '\111':
                    bs[73] = true;
                    break;
                case '\112':
                    bs[74] = true;
                    break;
                case '\113':
                    bs[75] = true;
                    break;
                case '\114':
                    bs[76] = true;
                    break;
                case '\115':
                    bs[77] = true;
                    break;
                case '\116':
                    bs[78] = true;
                    break;
                case '\117':
                    bs[79] = true;
                    break;
                case '\120':
                    bs[80] = true;
                    break;
                case '\121':
                    bs[81] = true;
                    break;
                case '\122':
                    bs[82] = true;
                    break;
                case '\123':
                    bs[83] = true;
                    break;
                case '\124':
                    bs[84] = true;
                    break;
                case '\125':
                    bs[85] = true;
                    break;
                case '\126':
                    bs[86] = true;
                    break;
                case '\127':
                    bs[87] = true;
                    break;
                case '\130':
                    bs[88] = true;
                    break;
                case '\131':
                    bs[89] = true;
                    break;
                case '\132':
                    bs[90] = true;
                    break;
                case '\133':
                    bs[91] = true;
                    break;
                case '\134':
                    bs[92] = true;
                    break;
                case '\135':
                    bs[93] = true;
                    break;
                case '\136':
                    bs[94] = true;
                    break;
                case '\137':
                    bs[95] = true;
                    break;
                case '\140':
                    bs[96] = true;
                    break;
                case '\141':
                    bs[97] = true;
                    break;
                case '\142':
                    bs[98] = true;
                    break;
                case '\143':
                    bs[99] = true;
                    break;
                case '\144':
                    bs[100] = true;
                    break;
                case '\145':
                    bs[101] = true;
                    break;
                case '\146':
                    bs[102] = true;
                    break;
                case '\147':
                    bs[103] = true;
                    break;
                case '\150':
                    bs[104] = true;
                    break;
                case '\151':
                    bs[105] = true;
                    break;
                case '\152':
                    bs[106] = true;
                    break;
                case '\153':
                    bs[107] = true;
                    break;
                case '\154':
                    bs[108] = true;
                    break;
                case '\155':
                    bs[109] = true;
                    break;
                case '\156':
                    bs[110] = true;
                    break;
                case '\157':
                    bs[111] = true;
                    break;
                case '\160':
                    bs[112] = true;
                    break;
                case '\161':
                    bs[113] = true;
                    break;
                case '\162':
                    bs[114] = true;
                    break;
                case '\163':
                    bs[115] = true;
                    break;
                case '\164':
                    bs[116] = true;
                    break;
                case '\165':
                    bs[117] = true;
                    break;
                case '\166':
                    bs[118] = true;
                    break;
                case '\167':
                    bs[119] = true;
                    break;
                case '\170':
                    bs[120] = true;
                    break;
                case '\171':
                    bs[121] = true;
                    break;
                case '\172':
                    bs[122] = true;
                    break;
                case '\173':
                    bs[123] = true;
                    break;
                case '\174':
                    bs[124] = true;
                    break;
                case '\175':
                    bs[125] = true;
                    break;
                case '\176':
                    bs[126] = true;
                    break;
                case '\177':
                    bs[127] = true;
                    break;
                case '\200':
                    bs[128] = true;
                    break;
                case '\201':
                    bs[129] = true;
                    break;
                case '\202':
                    bs[130] = true;
                    break;
                case '\203':
                    bs[131] = true;
                    break;
                case '\204':
                    bs[132] = true;
                    break;
                case '\205':
                    bs[133] = true;
                    break;
                case '\206':
                    bs[134] = true;
                    break;
                case '\207':
                    bs[135] = true;
                    break;
                case '\210':
                    bs[136] = true;
                    break;
                case '\211':
                    bs[137] = true;
                    break;
                case '\212':
                    bs[138] = true;
                    break;
                case '\213':
                    bs[139] = true;
                    break;
                case '\214':
                    bs[140] = true;
                    break;
                case '\215':
                    bs[141] = true;
                    break;
                case '\216':
                    bs[142] = true;
                    break;
                case '\217':
                    bs[143] = true;
                    break;
                case '\220':
                    bs[144] = true;
                    break;
                case '\221':
                    bs[145] = true;
                    break;
                case '\222':
                    bs[146] = true;
                    break;
                case '\223':
                    bs[147] = true;
                    break;
                case '\224':
                    bs[148] = true;
                    break;
                case '\225':
                    bs[149] = true;
                    break;
                case '\226':
                    bs[150] = true;
                    break;
                case '\227':
                    bs[151] = true;
                    break;
                case '\230':
                    bs[152] = true;
                    break;
                case '\231':
                    bs[153] = true;
                    break;
                case '\232':
                    bs[154] = true;
                    break;
                case '\233':
                    bs[155] = true;
                    break;
                case '\234':
                    bs[156] = true;
                    break;
                case '\235':
                    bs[157] = true;
                    break;
                case '\236':
                    bs[158] = true;
                    break;
                case '\237':
                    bs[159] = true;
                    break;
                case '\240':
                    bs[160] = true;
                    break;
                case '\241':
                    bs[161] = true;
                    break;
                case '\242':
                    bs[162] = true;
                    break;
                case '\243':
                    bs[163] = true;
                    break;
                case '\244':
                    bs[164] = true;
                    break;
                case '\245':
                    bs[165] = true;
                    break;
                case '\246':
                    bs[166] = true;
                    break;
                case '\247':
                    bs[167] = true;
                    break;
                case '\250':
                    bs[168] = true;
                    break;
                case '\251':
                    bs[169] = true;
                    break;
                case '\252':
                    bs[170] = true;
                    break;
                case '\253':
                    bs[171] = true;
                    break;
                case '\254':
                    bs[172] = true;
                    break;
                case '\255':
                    bs[173] = true;
                    break;
                case '\256':
                    bs[174] = true;
                    break;
                case '\257':
                    bs[175] = true;
                    break;
                case '\260':
                    bs[176] = true;
                    break;
                case '\261':
                    bs[177] = true;
                    break;
                case '\262':
                    bs[178] = true;
                    break;
                case '\263':
                    bs[179] = true;
                    break;
                case '\264':
                    bs[180] = true;
                    break;
                case '\265':
                    bs[181] = true;
                    break;
                case '\266':
                    bs[182] = true;
                    break;
                case '\267':
                    bs[183] = true;
                    break;
                case '\270':
                    bs[184] = true;
                    break;
                case '\271':
                    bs[185] = true;
                    break;
                case '\272':
                    bs[186] = true;
                    break;
                case '\273':
                    bs[187] = true;
                    break;
                case '\274':
                    bs[188] = true;
                    break;
                case '\275':
                    bs[189] = true;
                    break;
                case '\276':
                    bs[190] = true;
                    break;
                case '\277':
                    bs[191] = true;
                    break;
                case '\300':
                    bs[192] = true;
                    break;
                case '\301':
                    bs[193] = true;
                    break;
                case '\302':
                    bs[194] = true;
                    break;
                case '\303':
                    bs[195] = true;
                    break;
                case '\304':
                    bs[196] = true;
                    break;
                case '\305':
                    bs[197] = true;
                    break;
                case '\306':
                    bs[198] = true;
                    break;
                case '\307':
                    bs[199] = true;
                    break;
                case '\310':
                    bs[200] = true;
                    break;
                case '\311':
                    bs[201] = true;
                    break;
                case '\312':
                    bs[202] = true;
                    break;
                case '\313':
                    bs[203] = true;
                    break;
                case '\314':
                    bs[204] = true;
                    break;
                case '\315':
                    bs[205] = true;
                    break;
                case '\316':
                    bs[206] = true;
                    break;
                case '\317':
                    bs[207] = true;
                    break;
                case '\320':
                    bs[208] = true;
                    break;
                case '\321':
                    bs[209] = true;
                    break;
                case '\322':
                    bs[210] = true;
                    break;
                case '\323':
                    bs[211] = true;
                    break;
                case '\324':
                    bs[212] = true;
                    break;
                case '\325':
                    bs[213] = true;
                    break;
                case '\326':
                    bs[214] = true;
                    break;
                case '\327':
                    bs[215] = true;
                    break;
                case '\330':
                    bs[216] = true;
                    break;
                case '\331':
                    bs[217] = true;
                    break;
                case '\332':
                    bs[218] = true;
                    break;
                case '\333':
                    bs[219] = true;
                    break;
                case '\334':
                    bs[220] = true;
                    break;
                case '\335':
                    bs[221] = true;
                    break;
                case '\336':
                    bs[222] = true;
                    break;
                case '\337':
                    bs[223] = true;
                    break;
                case '\340':
                    bs[224] = true;
                    break;
                case '\341':
                    bs[225] = true;
                    break;
                case '\342':
                    bs[226] = true;
                    break;
                case '\343':
                    bs[227] = true;
                    break;
                case '\344':
                    bs[228] = true;
                    break;
                case '\345':
                    bs[229] = true;
                    break;
                case '\346':
                    bs[230] = true;
                    break;
                case '\347':
                    bs[231] = true;
                    break;
                case '\350':
                    bs[232] = true;
                    break;
                case '\351':
                    bs[233] = true;
                    break;
                case '\352':
                    bs[234] = true;
                    break;
                case '\353':
                    bs[235] = true;
                    break;
                case '\354':
                    bs[236] = true;
                    break;
                case '\355':
                    bs[237] = true;
                    break;
                case '\356':
                    bs[238] = true;
                    break;
                case '\357':
                    bs[239] = true;
                    break;
                case '\360':
                    bs[240] = true;
                    break;
                case '\361':
                    bs[241] = true;
                    break;
                case '\362':
                    bs[242] = true;
                    break;
                case '\363':
                    bs[243] = true;
                    break;
                case '\364':
                    bs[244] = true;
                    break;
                case '\365':
                    bs[245] = true;
                    break;
                case '\366':
                    bs[246] = true;
                    break;
                case '\367':
                    bs[247] = true;
                    break;
                case '\370':
                    bs[248] = true;
                    break;
                case '\371':
                    bs[249] = true;
                    break;
                case '\372':
                    bs[250] = true;
                    break;
                case '\373':
                    bs[251] = true;
                    break;
                case '\374':
                    bs[252] = true;
                    break;
                case '\375':
                    bs[253] = true;
                    break;
                case '\376':
                    bs[254] = true;
                    break;
                case '\377':
                    bs[255] = true;
                    break;
                default:
                    Tester.check(false, "should be here:" + i);
            }
        }
        for (int i = 0; i < bs.length; i++) {
            Tester.check(bs[i], "bs[" + i + "] wasn't caught --> '\\" + Integer.toOctalString(i) + "'");
        }
    }
}
