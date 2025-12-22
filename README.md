## Product Statistics

A console program (script) that parses JSON files with products, generates statistics by an attribute, and exports the results in XML format.

Domain area – products.

The main entity Product contains the following attributes:

name String  
price double  
category String  
tags String (a list of tags separated by commas: "eco, handmade, premium")  
brand String  

Example of an input JSON file:

```json
[
  {
    "name": "Smartphone Gimbal",
    "price": 196,
    "category": "Clothing",
    "tags": "budget",
    "brand": "Eabox"
  },
  {
    "name": "Pasta Sauce Mix",
    "price": 122,
    "category": "Toys",
    "tags": "eco",
    "brand": "Babbleblab"
  }
]
```

Example of an output file:
<pre>
&lt;statistics&gt;
  &lt;item&gt;
    &lt;value&gt;Kitchen&lt;/value&gt;
    &lt;count&gt;5&lt;/count&gt;
  &lt;/item&gt;
  &lt;item&gt;
    &lt;value&gt;Stationery&lt;/value&gt;
    &lt;count&gt;3&lt;/count&gt;
  &lt;/item&gt;
&lt;/statistics&gt;
</pre>

Experiment with the number of threads:

Data: 3 JSON files with 50 products in each (150 objects total).  
Results:
- 1 - 100 ms
- 2 - 110 ms
- 4 - 95 ms
- 8 - 95 ms

Conclusions: Speedup is observed when moving from 1 to 4 threads. When increasing the number of threads to 8, no further speedup was observed, which is expected for small JSON files.
