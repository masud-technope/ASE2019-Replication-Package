copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org eclipse swt internal image png lz block reader pnglzblockreader is last block islastblock compression type compressiontype uncompressed bytes remaining uncompressedbytesremaining png decoding data stream pngdecodingdatastream stream png huffman tables pnghuffmantables huffman tables huffmantables window window index windowindex copy index copyindex copy bytes remaining copybytesremaining uncompressed compressed fixed compressed dynamic compressed block length code length code distance code distance code code length code code length code length bases lengthbases extra length bits extralengthbits distance bases distancebases extra distance bits extradistancebits png lz block reader pnglzblockreader png decoding data stream pngdecodingdatastream stream stream stream is last block islastblock set window size setwindowsize window size windowsize window window size windowsize read next block header readnextblockheader is last block islastblock stream get next idat bit getnextidatbit compression type compressiontype stream get next idat bits getnextidatbits x ff xff compression type compressiontype stream error compression type compressiontype uncompressed stream get next idat byte getnextidatbyte stream get next idat byte getnextidatbyte stream get next idat byte getnextidatbyte stream get next idat byte getnextidatbyte stream error uncompressed bytes remaining uncompressedbytesremaining x ff xff x ff xff compression type compressiontype compressed dynamic huffman tables huffmantables png huffman tables pnghuffmantables get dynamic tables getdynamictables stream huffman tables huffmantables png huffman tables pnghuffmantables get fixed tables getfixedtables get next byte getnextbyte compression type compressiontype uncompressed uncompressed bytes remaining uncompressedbytesremaining read next block header readnextblockheader get next byte getnextbyte uncompressed bytes remaining uncompressedbytesremaining stream get next idat byte getnextidatbyte get next compressed byte getnextcompressedbyte compressed block is last block islastblock stream error read next block header readnextblockheader get next byte getnextbyte assert block at end assertblockatend compression type compressiontype uncompressed uncompressed bytes remaining uncompressedbytesremaining stream error copy bytes remaining copybytesremaining huffman tables huffmantables get next literal value getnextliteralvalue stream compressed block stream error assert compressed data at end assertcompresseddataatend assert block at end assertblockatend is last block islastblock read next block header readnextblockheader assert block at end assertblockatend get next compressed byte getnextcompressedbyte copy bytes remaining copybytesremaining window copy index copyindex window window index windowindex copy bytes remaining copybytesremaining copy index copyindex window index windowindex copy index copyindex window length copy index copyindex window index windowindex window length window index windowindex huffman tables huffmantables get next literal value getnextliteralvalue stream compressed block window window index windowindex x ff xff window index windowindex window index windowindex window length window index windowindex x ff xff compressed block read next block header readnextblockheader get next byte getnextbyte length code extra bits extrabits extra length bits extralengthbits length code length length bases lengthbases length code extra bits extrabits length stream get next idat bits getnextidatbits extra bits extrabits huffman tables huffmantables get next distance value getnextdistancevalue stream distance code stream error extra bits extrabits extra distance bits extradistancebits distance distance bases distancebases extra bits extrabits distance stream get next idat bits getnextidatbits extra bits extrabits copy index copyindex window index windowindex distance copy index copyindex copy index copyindex window length copy bytes remaining copybytesremaining length get next compressed byte getnextcompressedbyte stream error