copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org eclipse swt internal image org eclipse swt org eclipse swt graphics java win bmpfile format winbmpfileformat file format fileformat bmpfile header size bmpfileheadersize bmpheader fixed size bmpheaderfixedsize important colors importantcolors point pels per meter pelspermeter point compress num bytes numbytes bytes image data src storing dest starting technique comp image answer size compressed data compress comp src src offset srcoffset num bytes numbytes dest bmp rle compression comp compress rle compressrle data src src offset srcoffset num bytes numbytes dest bmp rle compression comp compress rle compressrle data src src offset srcoffset num bytes numbytes dest swt error swt error invalid image compress rle compressrle data src src offset srcoffset num bytes numbytes dest src offset srcoffset src offset srcoffset num bytes numbytes size left the byte thebyte find consecutive bytes left left left left src src scan include left store intervening data handled separately command dest pixels dest src size dest dest bytes pixels dest src size pad word dest size find length store left left left left the byte thebyte src left src the byte thebyte dest bytes pixels dest the byte thebyte size store bitmap codes dest dest dest size size compress rle compressrle data src src offset srcoffset num bytes numbytes dest src offset srcoffset src offset srcoffset num bytes numbytes size left the byte thebyte find consecutive bytes left left left left src src scan include left store intervening data handled separately command dest dest src size handled separately command dest dest src size dest dest dest src size pad word dest size find length store left left left left the byte thebyte src left src the byte thebyte dest dest the byte thebyte size store bitmap codes dest dest dest size size decompress data decompressdata src dest stride cmp bmp rle compression cmp decompress rle decompressrle data src src length stride dest dest length swt error swt error invalid image bmp rle compression cmp decompress rle decompressrle data src src length stride dest dest length swt error swt error invalid image swt error swt error invalid image decompress rle decompressrle data src num bytes numbytes stride dest dest size destsize num bytes numbytes dest size destsize len src x ff xff len len src x ff xff len stride bitmap delta src x ff xff src x ff xff stride absolute mode odd lengths supported len len len len len len len dest src word align len len len len the byte thebyte src len len dest the byte thebyte decompress rle decompressrle data src num bytes numbytes stride dest dest size destsize num bytes numbytes dest size destsize len src x ff xff len len src x ff xff len stride bitmap delta src x ff xff src x ff xff stride absolute mode len len len dest src word align len the byte thebyte src len len dest the byte thebyte len is file format isfileformat ledata input stream ledatainputstream stream header stream read header stream unread header info header size infoheadersize header x ff xff header x ff xff header x ff xff header x ff xff header header info header size infoheadersize bmpheader fixed size bmpheaderfixedsize exception load data loaddata info header infoheader width info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff height info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff bit count bitcount info header infoheader x ff xff info header infoheader x ff xff stride width bit count bitcount multiple stride stride data load data loaddata info header infoheader stride flip scan lines flipscanlines data stride height data load data loaddata info header infoheader stride height info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff data size datasize height stride data data size datasize cmp info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff bmp compression cmp input stream inputstream read data data size datasize swt error swt error invalid image ioexception swt error swt error compressed size compressedsize info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff compressed compressed size compressedsize input stream inputstream read compressed compressed size compressedsize swt error swt error invalid image ioexception swt error swt error decompress data decompressdata compressed data stride cmp data load file header loadfileheader header header input stream inputstream read short readshort header input stream inputstream read int readint header input stream inputstream read short readshort header input stream inputstream read short readshort header input stream inputstream read int readint ioexception swt error swt error header swt error swt error invalid image header image data imagedata load from byte stream loadfrombytestream file header fileheader load file header loadfileheader info header infoheader bmpheader fixed size bmpheaderfixedsize input stream inputstream read info header infoheader exception swt error swt error width info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff height info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff bit count bitcount info header infoheader x ff xff info header infoheader x ff xff palette data palettedata palette load palette loadpalette info header infoheader input stream inputstream get position getposition file header fileheader seek offset input stream inputstream skip file header fileheader input stream inputstream get position getposition ioexception swt error swt error data load data loaddata info header infoheader compression info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff important colors importantcolors info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff x pels per meter xpelspermeter info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff y pels per meter ypelspermeter info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff pels per meter pelspermeter point x pels per meter xpelspermeter y pels per meter ypelspermeter type compression compression swt image bmp rle swt image bmp image data imagedata image data imagedata internal width height bit count bitcount palette data type palette data palettedata load palette loadpalette info header infoheader depth info header infoheader x ff xff info header infoheader x ff xff depth num colors numcolors info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff num colors numcolors num colors numcolors depth num colors numcolors num colors numcolors buf num colors numcolors input stream inputstream read buf buf length swt error swt error invalid image ioexception swt error swt error palette from bytes palettefrombytes buf num colors numcolors depth palette data palettedata depth palette data palettedata x ff xff x ff xff x ff xff palette data palettedata x ff xff x ff xff x ff xff palette data palettedata palette from bytes palettefrombytes bytes num colors numcolors bytes offset bytesoffset rgb colors rgb num colors numcolors num colors numcolors colors rgb bytes bytes offset bytesoffset x ff xff bytes bytes offset bytesoffset x ff xff bytes bytes offset bytesoffset x ff xff bytes offset bytesoffset palette data palettedata colors answer array bmp representation device independent palette palette to bytes palettetobytes palette data palettedata pal pal colors pal colors length pal colors length bytes offset rgb col pal colors bytes offset col blue bytes offset col green bytes offset col red offset bytes unload image data stream compression strategy answer number bytes written unload data unloaddata image data imagedata image output stream outputstream comp total size totalsize comp unload data no compression unloaddatanocompression image bpl image width image depth bmp pads scanlines multiples bytes bmp bpl bmpbpl bpl image bpl imagebpl image bytes per line bytesperline compression space worst buf bmp bpl bmpbpl start src offset srcoffset image bpl imagebpl image height data image data total size totalsize buf buf offset image height line size linesize compress comp data src offset srcoffset bpl buf buf offset line size linesize buf length write buf buf offset buf offset system arraycopy buf buf buf offset line size linesize buf offset line size linesize total size totalsize line size linesize src offset srcoffset image bpl imagebpl buf offset write buf buf offset ioexception swt error swt error total size totalsize prepare image data unloading stream compression strategy answer number bytes written unload data no compression unloaddatanocompression image data imagedata image output stream outputstream bmp bpl bmpbpl bpl image width image depth bmp pads scanlines multiples bytes bmp bpl bmpbpl bpl lines per buf linesperbuf bmp bpl bmpbpl buf lines per buf linesperbuf bmp bpl bmpbpl data image data image bpl imagebpl image bytes per line bytesperline start data index dataindex image bpl imagebpl image height image depth image height lines per buf linesperbuf count image height lines per buf linesperbuf count count lines per buf linesperbuf buf offset bufoffset count w index windex w index windex bpl w index windex buf buf offset bufoffset w index windex data data index dataindex w index windex buf buf offset bufoffset w index windex data data index dataindex w index windex buf offset bufoffset bmp bpl bmpbpl data index dataindex image bpl imagebpl write buf buf offset bufoffset image height lines per buf linesperbuf tmp image height count tmp lines per buf linesperbuf tmp lines per buf linesperbuf buf offset bufoffset count system arraycopy data data index dataindex buf buf offset bufoffset bpl buf offset bufoffset bmp bpl bmpbpl data index dataindex image bpl imagebpl write buf buf offset bufoffset ioexception swt error swt error bmp bpl bmpbpl image height unload device independent image deviceindependentimage windows bmp format stream unload into byte stream unloadintobytestream image data imagedata image rgbs num cols numcols image depth image depth image depth image depth image depth image depth swt error swt error unsupported depth comp compression comp comp image depth comp image depth swt error swt error invalid image palette data palettedata pal image palette image depth image depth image depth pal is direct isdirect swt error swt error invalid image num cols numcols rgbs pal is direct isdirect swt error swt error invalid image num cols numcols pal colors length rgbs palette to bytes palettetobytes pal fill file header bfsize headers size headerssize bmpfile header size bmpfileheadersize bmpheader fixed size bmpheaderfixedsize file header fileheader signature file header fileheader file size filled file header fileheader reserved file header fileheader reserved file header fileheader offset data file header fileheader headers size headerssize rgbs file header fileheader rgbs length prepare data don rewind stream fill details byte array output stream bytearrayoutputstream byte array output stream bytearrayoutputstream unload data unloaddata image comp data to byte array tobytearray calculate file size file header fileheader file header fileheader data length write headers output stream outputstream write short writeshort file header fileheader output stream outputstream write int writeint file header fileheader output stream outputstream write short writeshort file header fileheader output stream outputstream write short writeshort file header fileheader output stream outputstream write int writeint file header fileheader ioexception swt error swt error output stream outputstream write int writeint bmpheader fixed size bmpheaderfixedsize output stream outputstream write int writeint image width output stream outputstream write int writeint image height output stream outputstream write short writeshort output stream outputstream write short writeshort image depth output stream outputstream write int writeint comp output stream outputstream write int writeint data length output stream outputstream write int writeint pels per meter pelspermeter output stream outputstream write int writeint pels per meter pelspermeter output stream outputstream write int writeint num cols numcols output stream outputstream write int writeint important colors importantcolors ioexception swt error swt error unload palette num cols numcols output stream outputstream write rgbs ioexception swt error swt error unload data output stream outputstream write data ioexception swt error swt error flip scan lines flipscanlines data stride height height stride height stride data data data data stride stride