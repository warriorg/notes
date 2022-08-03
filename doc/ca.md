# 实践

```bash
# 生成CA证书
# 生成CA证书私有key
openssl genrsa -out ca.key 4096
# 生成CA证书
openssl req -x509 -new -nodes -sha512 -days 3650 \
 -subj "/C=CN/ST=Jiangsu/L=Suzhou/O=example/OU=Personal/CN=yourdomain.com" \
 -key ca.key \
 -out ca.crt
 
# 生成服务器端证书
openssl genrsa -out yourdomain.com.key 4096
# 生成证书签名请求(CSR)
openssl req -sha512 -new \
    -subj "/C=CN/ST=Jiangsu/L=Suzhou/O=example/OU=Personal/CN=yourdomain.com" \
    -key yourdomain.com.key \
    -out yourdomain.com.csr
    
# 生成x509 v3扩展名文件
cat > v3.ext <<-EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
extendedKeyUsage = serverAuth
subjectAltName = @alt_names

[alt_names]
DNS.1=yourdomain.com
DNS.2=yourdomain
DNS.3=hostname
EOF

# 使用v3.ext为主机签发证书
openssl x509 -req -sha512 -days 3650 \
    -extfile v3.ext \
    -CA ca.crt -CAkey ca.key -CAcreateserial \
    -in yourdomain.com.csr \
    -out yourdomain.com.crt
    
# 查看证书内容
openssl rsa -in ca.key -text -noout
# 查看der格式证书的内容
openssl rsa -in ca.key -text -noout -inform der
    
```

# 专业术语

**CA** Certificate Authority 证书颁发机构,即颁发数字证书的机构。是负责发放和管理数字证书的权威机构，并作为电子商务交易中受信任的第三方，承担公钥体系中公钥的合法性检验的责任。

**X.509** 密码学里公钥证书的格式标准。X.509证书已应用在包括TLS/SSL在内的众多网络协议里，同时它也用在很多非在线应用场景里，比如电子签名服务。X.509证书里含有公钥、身份信息（比如网络主机名，组织的名称或个体名称等）和签名信息（可以是证书签发机构CA的签名，也可以是自签名）。

**PKCS** Public Key Cryptography Standards 公钥加密标准，此一标准的设计与发布皆由RSA信息安全公司所制定。

**.pem** 隐私增强型电子邮件格式，通常是Base64格式的。

**.cer, .crt, .der** 通常是DER二进制格式的。

**.p12** PKCS#12格式，包含证书的同时可能还包含私钥

**.pfx** PFX，PKCS#12之前的格式（通常用PKCS#12格式，比如由互联网信息服务产生的PFX文件）



## 数字证书中主题(Subject)中字段的含义

**CN** 公用名称 (Common Name) 简称, 对于 SSL 证书，一般为网站域名或IP地址；而对于代码签名证书则为申请单位名称；而对于客户端证书则为证书申请者的姓名； 

**O** 单位名称 (Organization Name), 对于 SSL 证书，一般为网站域名；而对于代码签名证书则为申请单位名称；而对于客户端单位证书则为证书申请者所在单位名称； 

**L** 所在城市 (Locality) 简称

**ST** 所在省份 (State/Provice) 简称

**C** 所在国家 (Country) 简称，只能是国家字母缩写，如中国：CN

**E** 电子邮件 (Email) 简称

**G** 多个姓名字段

**OU** 显示其他内容简称

```bash
openssl req -x509 -new -nodes -sha512 -days 3650 \
 -subj "/C=CN/ST=jiangsu/L=suzhou/O=xy/OU=Personal/CN=xy.dev" \
 -key ca.key \
 -out ca.crt
```

https://stackoverflow.com/questions/6464129/certificate-subject-x-509

## x509证书链

x509证书一般会用到三类文件，`key、csr、crt`。

- **key**是私用密钥，openssl格式，通常是rsa算法。
- **csr**是证书请求文件，用于申请证书。在制作csr文件的时候，必须使用自己的私钥来签署申请，还可以设定一个密钥。
- **crt**是CA认证后的证书文件（windows下面的csr，其实是crt），签署人用自己的key给你签署的凭证。



## PKCS标准汇总

| 版本     | 名称 | 简介  | 说明 |
| -------- | ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| PKCS #1  | 2.1  | RSA密码编译标准（RSA Cryptography Standard）                 | 定义了RSA的数理基础、公/私钥格式，以及加/解密、签/验章的流程。1.5版本曾经遭到攻击。 |
| PKCS #2  | -    | *弃用*                                                       | 原本是用以规范RSA加密摘要的转换方式，现已被纳入PKCS#1之中。  |
| PKCS #3  | 1.4  | DH密钥协议标准（Diffie-Hellman key agreement Standard）      | 规范以DH密钥协议为基础的密钥协议标准。其功能，可以让两方透过金议协议，拟定一把会议密钥(Session key)。 |
| PKCS #4  | -    | *弃用*                                                       | 原本用以规范转换RSA密钥的流程。已被纳入PKCS#1之中。          |
| PKCS #5  | 2.0  | 密码基植加密标准（Password-based Encryption Standard）       | 参见RFC 2898与PBKDF2。                                       |
| PKCS #6  | 1.5  | 证书扩展语法标准（Extended-Certificate Syntax Standard）     | 将原本X.509的证书格式标准加以扩展。                          |
| PKCS #7  | 1.5  | 密码消息语法标准（Cryptographic Message Syntax Standard）    | 参见RFC 2315。规范了以公开密钥基础设施（PKI）所产生之签名/密文之格式。其目的一样是为了拓展数字证书的应用。其中，包含了`S/MIME`与`CMS` |
| PKCS #8  | 1.2  | 私钥消息表示标准（Private-Key Information Syntax Standard）. | Apache读取证书私钥的标准。                                   |
| PKCS #9  | 2.0  | 选择属性格式（Selected Attribute Types）                     | 定义PKCS#6、7、8、10的选择属性格式。                         |
| PKCS #10 | 1.7  | 证书申请标准（Certification Request Standard）               | 参见RFC 2986。规范了向证书中心申请证书之CSR（certificate signing request）的格式。 |
| PKCS #11 | 2.20 | 密码设备标准接口（Cryptographic Token Interface (Cryptoki)） | 定义了密码设备的应用程序接口（API）之规格。                  |
| PKCS #12 | 1.0  | 个人消息交换标准（Personal Information Exchange Syntax Standard） | 定义了包含私钥与公钥证书（public key certificate）的文件格式。私钥采密码(password)保护。常见的PFX就履行了PKCS#12。 |
| PKCS #13 | –    | 椭圆曲线密码学标准（Elliptic curve cryptography Standard）   | 制定中。规范以椭圆曲线密码学为基础所发展之密码技术应用。椭圆曲线密码学是新的密码学技术，其强度与效率皆比现行以指数运算为基础之密码学算法来的优秀。然而，该算法的应用尚不普及。 |
| PKCS #14 | –    | 拟随机数产生器标准（Pseudo-random Number Generation）        | 制定中。规范拟随机数产生器的使用与设计。                     |
| PKCS #15 | 1.1  | 密码设备消息格式标准（Cryptographic Token Information Format Standard） | 定义了密码设备内部数据的组织结构。                           |