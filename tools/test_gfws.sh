json="json_xs"
#json="python -mjson.tool"

server="http://localhost:41297"
#server="http://attempto.ifi.uzh.ch:41297"

pgf="${server}/grammars/Go.pgf"
#pgf="${server}/tmp/Geography/Geography.pgf"
cloud="${server}/cloud"

lang=GoEng
#lang=GeographyAce

tree="n3"
#tree="baseText (sText (s (vpS Basel_NP (v2VP border_V2 Emaj__245_gi_NP))))"

input="three"
#input="Basel borders Basel ."

curl "$pgf?command=grammar" | $json

curl "$pgf?command=parse&cat=Number&from=${lang}&input=\"${input}\"" | $json

curl "$pgf?command=linearize&to=${lang}&tree=n3" | $json

curl "$pgf?command=translate&cat=Number&from=${lang}&to=GoApp&input=three" | $json

curl "$pgf?command=random&cat=Number&input=t" | $json

curl "$pgf?command=complete&cat=Number&input=t" | $json

curl "$pgf?command=abstrtree&tree=n3" > diagram_abstrtree.png

curl "$pgf?command=parsetree&tree=n3&from=${lang}&format=gv" > diagram_parsetree.dot

echo "alignment: ${tree}"
curl "$pgf?command=alignment&tree=${tree}&to=GoEst+GoEng&format=svg" > diagram_alignment.svg

echo "curl $cloud?dir=/tmp/dir/&command=upload&file=content"
curl "$cloud?dir=/tmp/dir/&command=upload&file=content"
