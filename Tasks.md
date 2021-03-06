Loopback query filter generator

示例
```json
{
    "fields": {"field1":  false, "field2":  false},
    "include": {"owner": [{"posts": "images"} , "orders"]},
    "where": {},
    "limit": 1,
    "order": "createdOn DESC",
    "offset": 0
}
```

# 需求说明
- Query生成器可以根据参数生成带有fields, include，where, limit，order的符合loopback query filter格式的json query。
- Query生成器的参数可以通过Builder动态配置。
- 使用ObjectMapper作为序列化工具。

# Tasks
### Query可以设置和生成带有limit和offset的json query
- 数据格式：`"offset": 0, "limit": 10`
- Sub-tasks
    - (1)Query设置了offset时，生成的json字符串中包含offset，且数值与设置一致 ✅
    - (1)Query设置了limit时，生成的json字符串中包含limit，且数值与设置一致 ✅
    - (1)Query未设置offset时，生成的json字符串中不包含offset ✅
    - (1)Query未设置limit时，生成的json字符串中不包含limit ✅
- 附：Criteria转换规则：`offset = (pageNumber - 1) * pageSize, limit = pageSize`
### Query可以设置和生成带有order的json query
- 数据格式：
    - (1)单个：`"order": "field_name <asc|desc>"`
    - (3)多个：`"order": ["field_name <asc|desc>", "field_name <asc|desc>", ...]`
- Sub-tasks
    - (1)Query设置了order为ASC时，生成的json字符串中包含order，field_name与设置一致且为ASC ✅
    - (1)Query设置了order为DESC时，生成的json字符串中包含order，field_name与设置一致且为DESC ✅
    - (1)Query未设置order时，生成的json字符串中不包含order ✅
    - (3)Query设置了多个order时，生成的json字符串中包含order，多个条目的field_name和order与设置一致
    - (5)Query为order设置了两个相同的field_name时，生成json字符串报错
### Query可以设置和生成带有include的json query
- 数据格式：
    - 单层单个relation：`"include": "relation_name"`
    - 单层多个relation：`"include": ["relation_name1", "relation_name2"]`
    - 两层单个relation：`"include": {"relation_name": "subrelation_name"}`
    - 三层单个relation：`"include": {"relation_name": {"subrelation_name": "sub-subrelation_name"}}`
    - 多层多个复合relation：`"include": ["relation_name1": [{"subrelation_name1": "sub-subrelation_name"}, "subrelation_name2"], "relation_name2"]`
- Sub-tasks:
    - (1)Query设置了include时，为单层单个relation时，生成的json符合格式要求 ✅
    - (1)Query未设置include时，生成的json字符串中不包含include ✅
    - (3)Query设置了include时，为单层多个relation时，生成的json符合格式要求
    - (3)Query设置了include时，为两层单个relation时，生成的json符合格式要求
    - (5)Query设置了include时，为三层单个relation时，生成的json符合格式要求
    - (5)Query设置了include时，为多层多个的复合relation时，生成的json符合格式要求
### Query可以设置和生成带有where的json query
- 数据格式：
    - (1)支持单个where参数类型为int,float,string,boolean,instant `"where": {"field_name": {"eq": "value"}}` ✅
    - (1)支持eq `"where": {"field_name": {"eq": "{value}"}}` ✅
    - (1)支持多个where参数`"where": {"field_name1": {"eq": "value1"}, "field_name2": {"eq": "value2"}}` ✅
    - (1)支持操作符and/or `{"where": {"and": [{"field_name1": "value1"}, {"field_name2": "value2"}]}}` ✅
    - (1)支持操作符like/nlike `"where": {"field_name": {"like": "{value}"}}` ✅
    - (1)支持操作符正则 `"where": {"field_name": {"regexp": "{regex}"}}` ✅
    - (3)支持操作符gt/gte/lt/lte `"where": {"field_name": {"gt": 1}}` ✅
    - (3)支持操作符between `"where": {"field_name": {"between": [1, 5]}}` ✅
    - (3)支持复合嵌套的操作符and/or ✅
        ```json
            {
                "where": {
                    "or": [
                        {"and": [{"field_name1": "value1"}, {"field_name2": "value2"}]},
                        {"field_name3": "value3"}
                    ]
                }
            }
        ```
    - (5)支持操作符ilike/nilike `"where": {"field_name": {"ilike": "{value}"}}`
    - (5)支持操作符inq/nin `"where": {"field_name", {"inq": ["value1", "value2", "value3"]}}`
    - (5)支持操作符neq `"where": {"field_name": {"neq": "{value}"}}`
- Sub-tasks: 
    - 参见数据格式
    - (1)Query未设置where时，生成的json字符串中不包含where
### 单独使用Where，也可以构建和生成的json用作Loopback的update,delete操作 ✅
- 数据格式：参见Query中的where使用
- Sub-tasks:
    - (3)独立构建where，可以生成符合要求格式的json
### Query可以设置和生成带有fields的json query
- 数据格式：
    - 单个field参数`"fields": {"field_name": false}`
    - 多个field参数`"fields": {"field_name": true, "field_name": true}`
- Sub-tasks: 
    - (5)参见数据格式
    - (1)Query未设置field时，生成的json字符串中不包含field
### Query可以设置和生成带有scope的json query（为了支持include with filter）
- 数据格式：
    - 
    ```json
      {
        "include": {
          "relation": "relation_name",
          "scope": {
            "where": {"operation": {"relation_field_name": "value"}}
          }
        }
      }
    ```
- Sub-tasks: 
    - (5)参见数据格式
    - (1)Query未设置field时，生成的json字符串中不包含field
