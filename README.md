## Sample Executions for Solution1

Case demonstrating where clause nested conditions

contents of input.json
```json
{
  "table": "employee",
  "columns": ["name", "age", "type"],
  "where": {
    "and": [
      { "operator": "like", "fieldName": "name", "fieldValue": "Zee%" },
      {
        "operator": "IN",
        "fieldName": "name",
        "fieldValue": ["Zeeshan", "Rohit", "Vyom"]
      },
      { "operator": "between", "fieldName": "age", "fieldValue": [29, 40] },
      { "operator": "not in", "fieldName": "age", "fieldValue": [35, 45] },
      {
        "or": [
          { "operator": "LTE", "fieldName": "age", "fieldValue": 29 },
          { "operator": "GTE", "fieldName": "age", "fieldValue": 40 }
        ]
      }
    ]
  }
}
```
```bash
$ java -jar app/build/libs/app.jar input.json
```
```sql
SELECT name, age, type FROM employee 
WHERE (name LIKE "Zee%" AND 
       name IN ("Zeeshan", "Rohit", "Vyom") AND 
       age BETWEEN 29 AND 40 AND 
       age NOT IN (35, 45) AND 
       (age <= 29 OR age >= 40))
```