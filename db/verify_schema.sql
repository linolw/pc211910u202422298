SELECT table_schema, table_name
FROM information_schema.tables
WHERE table_schema='facturease_db';
SELECT id, invoice_identifier, description, status, net_amount,
       calculated_tax, item_count, created_at, updated_at
FROM facturease_db.invoices ORDER BY id DESC;
