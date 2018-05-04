/**********************************************************************************
* Descrição: Script de adição de nova coluna na #{esquemaTabela} 
* Autor: #{autor}
* Data Criação: #{data}
**********************************************************************************/

-----------------------------
-- #{esquemaTabela}
-----------------------------
prompt "[LOG] >>>>>> Executando script de alteração da #{esquemaTabela}"

DECLARE
    COLUMN_EXISTS EXCEPTION;
    PRAGMA EXCEPTION_INIT (COLUMN_EXISTS , -01430);
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE #{esquemaTabela} ADD #{campo} #{tipo}';
    DBMS_OUTPUT.PUT_LINE('[SUCCESS] A coluna #{campo} adicionada com sucesso!');
EXCEPTION
    WHEN COLUMN_EXISTS THEN
        DBMS_OUTPUT.PUT_LINE('[WARN] A coluna #{campo} que está sendo incluída já existe na tabela.');
END;
/

COMMENT ON COLUMN #{esquemaTabela}.#{campo} IS '#{comentario}';

prompt "[LOG] <<<<<< Fim da execucao do script de alteração da #{esquemaTabela}"