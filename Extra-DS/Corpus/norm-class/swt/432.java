copyright ibm corporation rights reserved program accompanying materials terms eclipse license accompanies distribution http eclipse org legal epl html contributors ibm corporation initial api implementation org eclipse swt graphics org eclipse swt org eclipse swt internal gdip org eclipse swt internal win instances represent paths dimensional coordinate system paths continuous lines rectangles arcs cubic quadratic bezier curves glyphs paths application code explicitly invoke code path dispose code method release operating system resources managed instance instances longer required path resource resource path warning field platform dependent field swt api marked shared packages swt platforms accessed application code handle point f pointf current point currentpoint point f pointf start point startpoint point f pointf constructs empty path param device device allocate path exception illegal argument exception illegalargumentexception error argument device current device exception swterror error handles handle path dispose path device device device device device get device getdevice device swt error swt error argument device device device check gdip checkgdip handle gdip graphics path graphicspath gdip fill mode alternate fillmodealternate handle swt error swt error handles device tracking device object adds receiver circular elliptical arc lies rectangular area arc code start angle startangle code code arc angle arcangle code degrees angles interpreted degrees clock position positive counter clockwise rotation negative clockwise rotation center arc center rectangle origin code code code code size code width code code height code arguments arc covers area code width code pixels wide code height code pixels tall param coordinate upper left corner arc param coordinate upper left corner arc param width width arc param height height arc param start angle startangle angle param arc angle arcangle angular extent arc relative start angle exception swtexception error graphic disposed receiver disposed add arc addarc width height start angle startangle arc angle arcangle is disposed isdisposed swt error swt error graphic disposed width width width width height height height height width height arc angle arcangle gdip graphics path graphicspath add arc addarc handle width height start angle startangle arc angle arcangle gdip graphics path graphicspath get last point getlastpoint handle current point currentpoint adds receiver path parameter param path path add receiver exception illegal argument exception illegalargumentexception error argument parameter error invalid argument parameter disposed exception swtexception error graphic disposed receiver disposed add path addpath path path is disposed isdisposed swt error swt error graphic disposed path swt error swt error argument path is disposed isdisposed swt error swt error invalid argument todo expose connect gdip graphics path graphicspath add path addpath handle path handle current point currentpoint path current point currentpoint current point currentpoint path current point currentpoint adds receiver rectangle width height param coordinate rectangle add param coordinate rectangle add param width width rectangle add param height height rectangle add exception swtexception error graphic disposed receiver disposed add rectangle addrectangle width height is disposed isdisposed swt error swt error graphic disposed rect f rectf rect rect f rectf rect rect rect width width rect height height gdip graphics path graphicspath add rectangle addrectangle handle rect current point currentpoint current point currentpoint adds receiver pattern glyphs generated drawing string font starting point param string text param coordinate starting point param coordinate starting point param font font exception illegal argument exception illegalargumentexception error argument font error invalid argument font disposed exception swtexception error graphic disposed receiver disposed add string addstring string string font font is disposed isdisposed swt error swt error graphic disposed font swt error swt error argument font is disposed isdisposed swt error swt error invalid argument length string length buffer length string get chars getchars length buffer h dc hdc device internal gdip font gdipfont create gdip font creategdipfont h dc hdc font handle point f pointf point point f pointf point gdip font get size getsize gdip font gdipfont point family gdip font family fontfamily gdip font get family getfamily gdip font gdipfont family style gdip font get style getstyle gdip font gdipfont size gdip font get size getsize gdip font gdipfont gdip graphics path graphicspath add string addstring handle buffer length family style size point gdip graphics path graphicspath get last point getlastpoint handle current point currentpoint gdip font family fontfamily delete family gdip font delete gdip font gdipfont device internal dispose h dc hdc closes current path adding receiver current point path starting point path exception swtexception error graphic disposed receiver disposed close is disposed isdisposed swt error swt error graphic disposed gdip graphics path graphicspath close figure closefigure handle feature gdi close figure closefigure affect point get last point getlastpoint starting point subpath calling close figure closefigure remember subpath starting point current point currentpoint start point startpoint current point currentpoint start point startpoint returns code code point contained receiver outline code code point checked containment receiver outline outline code code point checked contained bounds closed area covered receiver param coordinate point test containment param coordinate point test containment param testing containment param outline controls wether check outline contained area path code code path point code code exception illegal argument exception illegalargumentexception error argument error invalid argument disposed exception swtexception error graphic disposed receiver disposed outline is disposed isdisposed swt error swt error graphic disposed swt error swt error argument is disposed isdisposed swt error swt error invalid argument todo transformation init gdip initgdip outline mode get poly fill mode getpolyfillmode handle winding gdip fill mode winding fillmodewinding gdip fill mode alternate fillmodealternate gdip graphics path graphicspath set fill mode setfillmode handle mode outline gdip graphics path graphicspath is outline visible isoutlinevisible handle data gdip pen gdippen data gdip graphics gdipgraphics gdip graphics path graphicspath is visible isvisible handle data gdip graphics gdipgraphics adds receiver cubic bezier curve based parameters param coordinate control point spline param coordinate control spline param coordinate control spline param coordinate control spline param coordinate point spline param coordinate point spline exception swtexception error graphic disposed receiver disposed cubic to cubicto is disposed isdisposed swt error swt error graphic disposed gdip graphics path graphicspath add bezier addbezier handle current point currentpoint current point currentpoint gdip graphics path graphicspath get last point getlastpoint handle current point currentpoint disposes operating system resources path applications dispose paths allocate dispose handle device is disposed isdisposed gdip graphics path graphicspath delete handle handle device tracking device dispose object device replaces elements parameter values describe smallest rectangle completely receiver bounding box param bounds array hold result exception illegal argument exception illegalargumentexception error argument parameter error invalid argument parameter small hold bounding box exception swtexception error graphic disposed receiver disposed get bounds getbounds bounds is disposed isdisposed swt error swt error graphic disposed bounds swt error swt error argument bounds length swt error swt error invalid argument rect f rectf rect rect f rectf gdip graphics path graphicspath get bounds getbounds handle rect bounds rect bounds rect bounds rect width bounds rect height replaces elements parameter values describe current point path param point array hold result exception illegal argument exception illegalargumentexception error argument parameter error invalid argument parameter small hold point exception swtexception error graphic disposed receiver disposed get current point getcurrentpoint point is disposed isdisposed swt error swt error graphic disposed point swt error swt error argument point length swt error swt error invalid argument point current point currentpoint point current point currentpoint returns device independent representation receiver path data pathdata receiver exception swtexception error graphic disposed receiver disposed path data pathdata path data pathdata get path data getpathdata is disposed isdisposed swt error swt error graphic disposed count gdip graphics path graphicspath get point count getpointcount handle gdip types gdiptypes count points count gdip graphics path graphicspath get path types getpathtypes handle gdip types gdiptypes count gdip graphics path graphicspath get path points getpathpoints handle points count types count types index typesindex count type gdip types gdiptypes close type gdip path point type path type mask pathpointtypepathtypemask gdip path point type start pathpointtypestart types types index typesindex swt path move close type gdip path point type close subpath pathpointtypeclosesubpath gdip path point type line pathpointtypeline types types index typesindex swt path close type gdip path point type close subpath pathpointtypeclosesubpath gdip path point type bezier pathpointtypebezier types types index typesindex swt path cubic close gdip types gdiptypes gdip path point type close subpath pathpointtypeclosesubpath close types types index typesindex swt path close types index typesindex types length new types newtypes types index typesindex system arraycopy types new types newtypes types index typesindex types new types newtypes path data pathdata result path data pathdata result types types result points points result adds receiver current point point param coordinate add param coordinate add exception swtexception error graphic disposed receiver disposed line to lineto is disposed isdisposed swt error swt error graphic disposed gdip graphics path graphicspath add line addline handle current point currentpoint current point currentpoint gdip graphics path graphicspath get last point getlastpoint handle current point currentpoint returns code code path disposed code code method dispose path path disposed error invoke method path code code path disposed code code is disposed isdisposed handle sets current point receiver point note starts path param coordinate point param coordinate point exception swtexception error graphic disposed receiver disposed move to moveto is disposed isdisposed swt error swt error graphic disposed current point currentpoint start point startpoint current point currentpoint start point startpoint adds receiver quadratic curve based parameters param coordinate control point spline param coordinate control point spline param coordinate point spline param coordinate point spline exception swtexception error graphic disposed receiver disposed quad to quadto is disposed isdisposed swt error swt error graphic disposed current point currentpoint current point currentpoint current point currentpoint current point currentpoint current point currentpoint current point currentpoint gdip graphics path graphicspath add bezier addbezier handle current point currentpoint current point currentpoint gdip graphics path graphicspath get last point getlastpoint handle current point currentpoint returns string concise human readable description receiver string representation receiver string to string tostring is disposed isdisposed path disposed path handle