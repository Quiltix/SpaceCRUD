CREATE OR REPLACE FUNCTION update_resource_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE resources
    SET last_updated = NOW()
    WHERE id = NEW.resource_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_auto_update_resource_time
AFTER INSERT OR UPDATE ON resource_logs
FOR EACH ROW
EXECUTE FUNCTION update_resource_timestamp();