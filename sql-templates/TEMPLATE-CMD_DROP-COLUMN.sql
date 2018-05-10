DECLARE
    COLUMN_NOT_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (COLUMN_NOT_EXISTS , -00904);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{Esquema e Tabela@Digite o esquema e a tabela. Ex.: esquema.tabela} DROP COLUMN #{Campo@Campo a ser excluído@30}';

    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A coluna #{Campo@Campo a ser excluído@30} foi excluída com sucesso!');
    
EXCEPTION
    WHEN COLUMN_NOT_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna #{Campo@Campo a ser excluído@30} que está sendo excluída já não existe na tabela.');
END;
/