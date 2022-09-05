# 替换OR, 使用exists

```sql
select * from write_off_accounting_bom t1 where EXISTS ( 
	select 1 from ( 
  	SELECT 'xxxx' material_no, 'yyyy' unit_consumption_version
  	union 
  	SELECT 'xxxxx' material_no, 'yyyy' unit_consumption_version
  ) t2 
  where t1.export_material_no = t2.material_no and t1.unit_consumption_version = t2.unit_consumption_version)
```