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

