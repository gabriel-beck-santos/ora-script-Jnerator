/**********************************************************************************
* Descrição: Descrição
* Autor: Gabriel Beck dos Santos
* Data Criação: 2018-04-04
**********************************************************************************/

-----------------------------
-- #{tabela}
-----------------------------
prompt "[LOG] >>>>>> Executando script de ALTER TABLE SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO"

DECLARE
    COLUMN_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (COLUMN_EXISTS , -01430);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO ADD IDEN_PENDENCIA NUMBER(12, 0)';
    DBMS_OUTPUT.PUT_LINE('[SUCESS] A tabela SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO alterada com sucesso!');
EXCEPTION
    WHEN COLUMN_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna que está sendo incluída já existe na tabela SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO.');
END;
/

prompt "[LOG] <<<<<< Fim da execucao do script de ALTER TABLE SEFAZ_CCI.TA_DOCUMENTO_APRESENTADO"
