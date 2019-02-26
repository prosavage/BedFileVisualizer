# BedFileVisualizer

This tool is still under construction.

This program aims to provide people with the ability to visualize BED files. 
If you do not know what a BED file is please see the About Bed Files section.
The Goal of the program is to help researchers find rare diseases easily by first
performing functions such as intersection, window, multi-intersection on the bed files
Then by visualizing said bed files below:

[graph-rainbow]: https://i.imgur.com/eHkIRPN.png 'The graph seems to not be found :('

[graph-heat-map]: https://i.imgur.com/rm3t04e.png 'The graph seems to not be found :('

[graph-gantt]: https://i.imgur.com/pYb9eHN.png 'The graph seems to not be found :('



###About Bed Files
BED (Browser Extensible Data) format provides a flexible way to define the data lines that are displayed in an annotation track.
BED lines have three required fields and nine additional optional fields. The number of fields per line must be consistent 
throughout any single set of data in an annotation track. The order of the optional fields is binding: lower-numbered fields
must always be populated if higher-numbered fields are used.

Here is an example BED File snippet:
```
chr7    127471196  127472363  Pos1  0  +  127471196  127472363  255,0,0
chr7    127472363  127473530  Pos2  0  +  127472363  127473530  255,0,0
chr7    127473530  127474697  Pos3  0  +  127473530  127474697  255,0,0
chr7    127474697  127475864  Pos4  0  +  127474697  127475864  255,0,0
chr7    127475864  127477031  Neg1  0  -  127475864  127477031  0,0,255
chr7    127477031  127478198  Neg2  0  -  127477031  127478198  0,0,255
chr7    127478198  127479365  Neg3  0  -  127478198  127479365  0,0,255
chr7    127479365  127480532  Pos5  0  +  127479365  127480532  255,0,0
chr7    127480532  127481699  Neg4  0  -  127480532  127481699  0,0,255
```