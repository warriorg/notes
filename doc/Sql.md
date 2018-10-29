```sql
-- 创建表
CREATE TABLE PROPERTY_PATROL (
   PATROL_SID           NVARCHAR(50)         NOT NULL,
   APARTMENT_SID        NVARCHAR(50)         NULL,
   PATROL_NAME          NVARCHAR(50)         NULL,
   PATROL_MAC           NVARCHAR(50)         NULL,
   PATROL_PWD           NVARCHAR(50)         NULL,
   PATROL_OPEN          NVARCHAR(50)         NULL,
   PATROL_CLOSE         NVARCHAR(50)         NULL,
   PATROL_ENABLED       INT                  NULL,
   PATROL_SUPPLIER      NVARCHAR(50)         NULL,
   REMARK               NVARCHAR(200)        NULL,
   CREATED_ON           DATETIME             NULL,
   CREATEDBY            NVARCHAR(50)         NULL,
   MODIFIED_ON          DATETIME             NULL,
   MODIFIEDBY           NVARCHAR(50)         NULL,
   CONSTRAINT PK_PROPERTY_PATROL_KEY PRIMARY KEY (PATROL_SID)
)
go
```

