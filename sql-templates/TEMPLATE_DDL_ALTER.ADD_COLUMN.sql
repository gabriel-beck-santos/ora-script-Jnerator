/**********************************************************************************
* Descrição: #{descricao}
* Autor: #{autor}
* Data Criação: #{data}
**********************************************************************************/

-----------------------------
-- #{esquematabela}
-----------------------------
prompt "[LOG] >>>>>> Executando script de ALTER TABLE #{esquematabela} asdasd #{esquematabela} dadsd #{esquematabela}"

DECLARE
    COLUMN_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (COLUMN_EXISTS , -01430);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{esquematabela} ADD #{campotipo} ';
    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A coluna alterada com sucesso!');
EXCEPTION
    WHEN COLUMN_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna que está sendo incluída já existe na tabela.');
END;
/

COMMENT ON COLUMN #{esquematabela} IS #{comentario};

prompt "[LOG] <<<<<< Fim da execucao do script de ALTER TABLER #{esquematabela}"