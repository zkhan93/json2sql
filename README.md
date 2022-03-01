## Sample Executions for Solution2

Case demonstrating joins and inner query

contents of input.json
```json
{
  "table": "employee",
  "columns": [
    { "name": "name", "table": "employee" },
    { "name": "age", "table": "employee" },
    { "name": "country_name", "table": "country" },
    { "name": "city", "table": "address" }
  ],
  "joins": [
    {
      "type": "INNER",
      "table": "address",
      "condition": {
        "operator": "equal",
        "right": { "name": "id", "table": "employee" },
        "left": { "name": "employee_id", "table": "address" }
      }
    },
    {
      "type": "INNER",
      "table": "country",
      "condition": {
        "operator": "equal",
        "right": { "name": "country_code", "table": "address" },
        "left": { "name": "code", "table": "country" }
      }
    }
  ],
  "where": {
    "and": [
      {
        "operator": "not equal",
        "fieldName": { "table": "employee", "name": "name" },
        "fieldValue": "Zee3"
      },
      {
        "operator": "like",
        "fieldName": { "table": "employee", "name": "name" },
        "fieldValue": "Zee%"
      },
      {
        "operator": "in",
        "fieldName": { "table": "employee", "name": "name" },
        "fieldValue": {
          "table": "employee",
          "columns": [{ "name": "name", "table": "employee" }]
        }
      }
    ]
  }
}

```

with MySQL dialect
```bash
$ java -jar app/build/libs/app.jar input.json mysql
```
```sql
SELECT employee.name, employee.age, country.country_name, address.city FROM employee 
INNER JOIN address ON employee.id = address.employee_id 
INNER JOIN country ON address.country_code = country.code 
WHERE (employee.name <> "Zee3" AND employee.name LIKE "Zee%" AND employee.name IN (SELECT employee.name FROM employee))
```

with PostgreSQL dialect
```bash
$ java -jar app/build/libs/app.jar input.json postgresql
```
```sql
SELECT employee.name, employee.age, country.country_name, address.city FROM employee 
INNER JOIN address ON employee.id = address.employee_id 
INNER JOIN country ON address.country_code = country.code 
WHERE (employee.name != 'Zee3' AND employee.name LIKE 'Zee%' AND employee.name IN (SELECT employee.name FROM employee))
```
Note the change in quote character and not equal operator symbol