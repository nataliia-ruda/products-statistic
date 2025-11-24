Product Statistics

Консольна програма-скрипт, яка парсить JSON-файли з товарами, формує статистику за атрибутом та експортує результати в XML-форматі.

Предметна область - товари.

Основна сутність Product містить атрибути:

- name String
- price double
- category String
- tags String (список тегів, розділених комами: "eco, handmade, premium")
- brand String

Приклад вхідного JSON-файла:
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

Приклад вихідного файлу:
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

Експеримент з кількістю потоків:

Дані: 3 JSON-файли по 50 товарів у кожному (150 об'єктів).
Результати:
Результати:
- 1 - 100 ms
- 2 - 110 ms
- 4 - 95 ms
- 8 - 95 ms

Висновки:
Прискорення спостерігається при переході від 1 до 4 потоків. При збільшенні кількості потоків до 8 пришвидшення не спостерігалося, що є очікуваним для невеликих JSON-файлів.
