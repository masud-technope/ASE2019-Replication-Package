copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org eclipse swt internal image org eclipse swt org eclipse swt graphics java win icofile format winicofileformat file format fileformat convert pad convertpad data width height depth pad new pad newpad pad new pad newpad data stride width depth bpl stride pad pad pad new bpl newbpl stride new pad newpad new pad newpad new pad newpad new data newdata height new bpl newbpl src index srcindex dest index destindex height system arraycopy data src index srcindex new data newdata dest index destindex new bpl newbpl src index srcindex bpl dest index destindex new bpl newbpl new data newdata answer size bytes file representation icon icon size iconsize image data imagedata shape data stride shapedatastride width depth mask data stride maskdatastride width data size datasize shape data stride shapedatastride mask data stride maskdatastride height palette size palettesize palette colors palette colors length win bmpfile format winbmpfileformat bmpheader fixed size bmpheaderfixedsize palette size palettesize data size datasize is file format isfileformat ledata input stream ledatainputstream stream header stream read header stream unread header header header header header exception is valid icon isvalidicon image data imagedata depth palette is direct isdirect size palette colors length size size size size palette is direct isdirect load file header loadfileheader ledata input stream ledatainputstream byte stream bytestream file header fileheader file header fileheader byte stream bytestream read short readshort file header fileheader byte stream bytestream read short readshort file header fileheader byte stream bytestream read short readshort ioexception swt error swt error file header fileheader file header fileheader swt error swt error invalid image num icons numicons file header fileheader num icons numicons swt error swt error invalid image num icons numicons load file header loadfileheader ledata input stream ledatainputstream byte stream bytestream has header hasheader file header fileheader has header hasheader file header fileheader byte stream bytestream read short readshort file header fileheader byte stream bytestream read short readshort file header fileheader file header fileheader file header fileheader byte stream bytestream read short readshort ioexception swt error swt error file header fileheader file header fileheader swt error swt error invalid image num icons numicons file header fileheader num icons numicons swt error swt error invalid image num icons numicons image data imagedata load from byte stream loadfrombytestream num icons numicons load file header loadfileheader input stream inputstream headers load icon headers loadiconheaders num icons numicons image data imagedata icons image data imagedata headers length icons length icons load icon loadicon headers icons load icon stream image data imagedata load icon loadicon icon header iconheader info header infoheader load info header loadinfoheader icon header iconheader win bmpfile format winbmpfileformat bmp format bmpformat win bmpfile format winbmpfileformat bmp format bmpformat input stream inputstream input stream inputstream palette data palettedata palette bmp format bmpformat load palette loadpalette info header infoheader shape data shapedata bmp format bmpformat load data loaddata info header infoheader width info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff height info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff depth info header infoheader x ff xff info header infoheader x ff xff info header infoheader info header infoheader mask data maskdata bmp format bmpformat load data loaddata info header infoheader mask data maskdata convert pad convertpad mask data maskdata width height bit invert data bitinvertdata mask data maskdata mask data maskdata length image data imagedata internal width height depth palette shape data shapedata mask data maskdata swt image ico load icon headers loadiconheaders num icons numicons headers num icons numicons num icons numicons headers input stream inputstream read headers input stream inputstream read headers input stream inputstream read short readshort headers input stream inputstream read short readshort headers input stream inputstream read short readshort headers input stream inputstream read int readint headers input stream inputstream read int readint ioexception swt error swt error headers load info header loadinfoheader icon header iconheader width icon header iconheader height icon header iconheader number colors high num colors numcolors icon header iconheader represents colors num colors numcolors num colors numcolors num colors numcolors num colors numcolors num colors numcolors num colors numcolors num colors numcolors swt error swt error invalid image input stream inputstream get position getposition icon header iconheader seek offset input stream inputstream skip icon header iconheader input stream inputstream get position getposition ioexception swt error swt error info header infoheader win bmpfile format winbmpfileformat bmpheader fixed size bmpheaderfixedsize input stream inputstream read info header infoheader ioexception swt error swt error info header infoheader x ff xff info header infoheader x ff xff swt error swt error invalid image info width infowidth info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info height infoheight info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff info header infoheader x ff xff bit count bitcount info header infoheader x ff xff info header infoheader x ff xff height info height infoheight bit count bitcount height width info width infowidth height info height infoheight bit count bitcount bit count bitcount bit count bitcount bit count bitcount bit count bitcount swt error swt error invalid image info header infoheader height x ff xff info header infoheader height x ff xff info header infoheader height x ff xff info header infoheader height x ff xff info header infoheader unload single icon unload icon unloadicon image data imagedata icon size image sizeimage icon width icon depth icon width icon height output stream outputstream write int writeint win bmpfile format winbmpfileformat bmpheader fixed size bmpheaderfixedsize output stream outputstream write int writeint icon width output stream outputstream write int writeint icon height output stream outputstream write short writeshort output stream outputstream write short writeshort icon depth output stream outputstream write int writeint output stream outputstream write int writeint size image sizeimage output stream outputstream write int writeint output stream outputstream write int writeint output stream outputstream write int writeint icon palette colors icon palette colors length output stream outputstream write int writeint ioexception swt error swt error rgbs win bmpfile format winbmpfileformat palette to bytes palettetobytes icon palette output stream outputstream write rgbs ioexception swt error swt error unload shape data unloadshapedata icon unload mask data unloadmaskdata icon unload icon header icon calculating offset unload icon header unloadiconheader image data imagedata header size headersize offset header size headersize icon size iconsize icon size iconsize output stream outputstream write byte writebyte width output stream outputstream write byte writebyte height output stream outputstream write short writeshort palette colors palette colors length output stream outputstream write short writeshort output stream outputstream write short writeshort output stream outputstream write int writeint icon size iconsize output stream outputstream write int writeint offset ioexception swt error swt error unload into byte stream unloadintobytestream image data imagedata image is valid icon isvalidicon image swt error swt error invalid image output stream outputstream write short writeshort output stream outputstream write short writeshort output stream outputstream write short writeshort ioexception swt error swt error unload icon header unloadiconheader image unload icon unloadicon image unload mask data icon data flipped vertically inverted unload mask data unloadmaskdata image data imagedata icon image data imagedata mask icon get transparency mask gettransparencymask bpl icon width pad mask scanline pad scanlinepad src bpl srcbpl bpl pad pad pad dest bpl destbpl bpl buf dest bpl destbpl offset icon height src bpl srcbpl data mask data icon height system arraycopy data offset buf bpl bit invert data bitinvertdata buf bpl output stream outputstream write buf dest bpl destbpl offset src bpl srcbpl ioexception swt error swt error unload shape data icon data flipped vertically unload shape data unloadshapedata image data imagedata icon bpl icon width icon depth pad icon scanline pad scanlinepad src bpl srcbpl bpl pad pad pad dest bpl destbpl bpl buf dest bpl destbpl offset icon height src bpl srcbpl data icon data icon height system arraycopy data offset buf bpl output stream outputstream write buf dest bpl destbpl offset src bpl srcbpl ioexception swt error swt error