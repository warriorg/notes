## SQLPlus

```bash
sqlplus username/password@domain:port/serviceName    # 连接数据库

expdp username/password@serviceName dumpfile=edi_sdc.dmp schemas=EDI_SDC   # 备份oracle
```

## 执行计划

```sql
EXPLAIN PLAN FOR SELECT * from T_CERTIFICATE_DEDUCT
SELECT plan_table_output FROM TABLE(DBMS_XPLAN.DISPLAY('PLAN_TABLE'));
```



## Q&A

###  ORA-01502: index 'SIMPLEE_PK_SID' or partition of such index is in unusable state

```bash
# 因为索引状态为UNUSABLE引起的 你可以通过下面SQL，查看索引的状态
SELECT OWNER, INDEX_NAME,STATUS  FROM DBA_INDEXES WHERE INDEX_NAME='INDEX_NAME' 
SELECT OWNER, INDEX_NAME,STATUS  FROM ALL_INDEXES WHERE INDEX_NAME='INDEX_NAME' 
SELECT  INDEX_NAME,STATUS  FROM USER_INDEXES WHERE INDEX_NAME='INDEX_NAME' 
# 使用重建索引解决
ALTER INDEX "SIMPLEE_PK_SID" REBUILD
```

