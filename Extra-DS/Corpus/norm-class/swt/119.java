copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org eclipse swt widgets org eclipse swt internal win org eclipse swt org eclipse swt graphics image list imagelist handle style ref count refcount image images color flags is win ce iswince color flags ilc color flags comctl major is app themed isappthemed flags ilc color h dc hdc get dc getdc bits get device caps getdevicecaps h dc hdc bitspixel planes get device caps getdevicecaps h dc hdc planes release dc releasedc h dc hdc depth bits planes depth flags ilc color flags ilc color flags ilc color flags ilc color flags ilc color flags ilc color color flags flags image list imagelist style style style flags color flags ilc mask style swt left flags ilc mirror handle image list imagelist create flags images image add image image count image list imagelist get image count getimagecount handle count images images is disposed isdisposed images images count rectangle rect image get bounds getbounds image list imagelist set icon size seticonsize handle rect width rect height image count images length image new images newimages image images length system arraycopy images new images newimages images length images new images newimages images image add ref addref ref count refcount copy bitmap copybitmap h image himage width height bitmap bitmap get object getobject h image himage bitmap sizeof h dc hdc get dc getdc hdc create compatible dc createcompatibledc h dc hdc select object selectobject hdc h image himage hdc create compatible dc createcompatibledc h dc hdc h bitmap hbitmap create compatible bitmap createcompatiblebitmap h dc hdc width height select object selectobject hdc h bitmap hbitmap width bm width bmwidth height bm height bmheight is win ce iswince set stretch blt mode setstretchbltmode hdc coloroncolor stretch blt stretchblt hdc width height hdc bm width bmwidth bm height bmheight srccopy bit blt bitblt hdc width height hdc srccopy delete dc deletedc hdc delete dc deletedc hdc release dc releasedc h dc hdc h bitmap hbitmap copy icon copyicon h image himage width height is win ce iswince swt error swt error implemented h icon hicon copy image copyimage h image himage image icon width height h icon hicon h icon hicon h image himage copy with alpha copywithalpha h bitmap hbitmap background alpha data alphadata dest width destwidth dest height destheight bitmap bitmap get object getobject h bitmap hbitmap bitmap sizeof src width srcwidth bm width bmwidth src height srcheight bm height bmheight create resources hdc get dc getdc src hdc srchdc create compatible dc createcompatibledc hdc old src bitmap oldsrcbitmap select object selectobject src hdc srchdc h bitmap hbitmap mem hdc memhdc create compatible dc createcompatibledc hdc bitmapinfoheader bmi header bmiheader bitmapinfoheader bmi header bmiheader bi size bisize bitmapinfoheader sizeof bmi header bmiheader bi width biwidth math max src width srcwidth dest width destwidth bmi header bmiheader bi height biheight math max src height srcheight dest height destheight bmi header bmiheader bi planes biplanes bmi header bmiheader bi bit count bibitcount bmi header bmiheader bi compression bicompression rgb bmi bitmapinfoheader sizeof move memory movememory bmi bmi header bmiheader bitmapinfoheader sizeof p bits pbits mem dib memdib create dibsection createdibsection bmi dib rgb colors p bits pbits mem dib memdib swt error swt error handles old mem bitmap oldmembitmap select object selectobject mem hdc memhdc mem dib memdib bitmap dib bm dibbm bitmap get object getobject mem dib memdib bitmap sizeof dib bm dibbm size in bytes sizeinbytes dib bm dibbm bm width bytes bmwidthbytes dib bm dibbm bm height bmheight foreground pixels bit blt bitblt mem hdc memhdc src width srcwidth src height srcheight src hdc srchdc srccopy src data srcdata size in bytes sizeinbytes move memory movememory src data srcdata dib bm dibbm bm bits bmbits size in bytes sizeinbytes merge alpha channel place alpha data alphadata spinc dib bm dibbm bm width bytes bmwidthbytes src width srcwidth src height srcheight src width srcwidth src data srcdata alpha data alphadata spinc trans red transred background x ff xff trans green transgreen background x ff xff trans blue transblue background x ff xff spinc dib bm dibbm bm width bytes bmwidthbytes src width srcwidth src height srcheight src width srcwidth src data srcdata src data srcdata trans red transred src data srcdata trans green transgreen src data srcdata trans blue transblue spinc move memory movememory dib bm dibbm bm bits bmbits src data srcdata size in bytes sizeinbytes stretch src width srcwidth dest width destwidth src height srcheight dest height destheight set stretch blt mode setstretchbltmode mem hdc memhdc coloroncolor stretch blt stretchblt mem hdc memhdc dest width destwidth dest height destheight mem hdc memhdc src width srcwidth src height srcheight srccopy free resources select object selectobject mem hdc memhdc old mem bitmap oldmembitmap delete dc deletedc mem hdc memhdc select object selectobject src hdc srchdc old src bitmap oldsrcbitmap delete dc deletedc src hdc srchdc release dc releasedc hdc mem dib memdib create mask createmask h bitmap hbitmap dest width destwidth dest height destheight background transparent pixel transparentpixel bitmap bitmap get object getobject h bitmap hbitmap bitmap sizeof src width srcwidth bm width bmwidth src height srcheight bm height bmheight h mask hmask create bitmap createbitmap dest width destwidth dest height destheight h dc hdc get dc getdc hdc create compatible dc createcompatibledc h dc hdc background select object selectobject hdc h bitmap hbitmap image palette multiple entries color entries transparent pixel transparentpixel entry transparent avoid problem temporarily change image palette palette transparent pixel transparentpixel white black is dib isdib bm bits bmbits original colors originalcolors is win ce iswince transparent pixel transparentpixel is dib isdib bm bits pixel bmbitspixel max colors maxcolors bm bits pixel bmbitspixel old colors oldcolors max colors maxcolors get dibcolor table getdibcolortable hdc max colors maxcolors old colors oldcolors offset transparent pixel transparentpixel new colors newcolors old colors oldcolors length new colors newcolors offset x ff xff new colors newcolors offset x ff xff new colors newcolors offset x ff xff set dibcolor table setdibcolortable hdc max colors maxcolors new colors newcolors original colors originalcolors old colors oldcolors set bk color setbkcolor hdc x ffffff xffffff set bk color setbkcolor hdc background hdc create compatible dc createcompatibledc h dc hdc select object selectobject hdc h mask hmask dest width destwidth src width srcwidth dest height destheight src height srcheight is win ce iswince set stretch blt mode setstretchbltmode hdc coloroncolor stretch blt stretchblt hdc dest width destwidth dest height destheight hdc src width srcwidth src height srcheight srccopy bit blt bitblt hdc dest width destwidth dest height destheight hdc srccopy delete dc deletedc hdc original palette original colors originalcolors set dibcolor table setdibcolortable hdc bm bits pixel bmbitspixel original colors originalcolors h old bitmap holdbitmap select object selectobject hdc h mask hmask pat blt patblt hdc dest width destwidth dest height destheight blackness select object selectobject hdc h old bitmap holdbitmap release dc releasedc h dc hdc delete dc deletedc hdc h mask hmask dispose handle image list imagelist destroy handle handle images image images get style getstyle style get handle gethandle handle point get image size getimagesize image list imagelist get icon size geticonsize handle point index of indexof image image count image list imagelist get image count getimagecount handle count images images is disposed isdisposed images images images equals image image image count image list imagelist get image count getimagecount handle count image image count images image remove count image list imagelist get image count getimagecount handle count image list imagelist remove handle system arraycopy images images count images remove ref removeref ref count refcount image image count h image himage image handle image list imagelist get icon size geticonsize handle image type swt bitmap note image size match image list icon size h bitmap hbitmap h mask hmask image data imagedata data image get image data getimagedata data get transparency type gettransparencytype swt transparency alpha comctl major h bitmap hbitmap copy with alpha copywithalpha h image himage data alpha data alphadata h bitmap hbitmap copy bitmap copybitmap h image himage h mask hmask display create mask from alpha createmaskfromalpha data swt transparency pixel background color color image get background getbackground color background color handle h bitmap hbitmap copy bitmap copybitmap h image himage h mask hmask create mask createmask h image himage background data transparent pixel transparentpixel swt transparency h bitmap hbitmap copy bitmap copybitmap h image himage count h mask hmask create mask createmask h image himage count image list imagelist add handle h bitmap hbitmap h mask hmask note mask replaced transparency image list imagelist replace handle h bitmap hbitmap h mask hmask h mask hmask delete object deleteobject h mask hmask h bitmap hbitmap h image himage delete object deleteobject h bitmap hbitmap swt icon is win ce iswince image list imagelist replace icon replaceicon handle count h image himage h icon hicon copy icon copyicon h image himage image list imagelist replace icon replaceicon handle count h icon hicon destroy icon destroyicon h icon hicon size result count image list imagelist get image count getimagecount handle count images images is disposed isdisposed images images result result