CREATE OR REPLACE VIEW v_spacecraft_resource_status AS
SELECT
    s.name AS spacecraft_name,
    s.spacecraft_status,
    rt.name AS resource_type,
    r.current_quantity,
    r.max_capacity,
    ROUND((r.current_quantity / r.max_capacity * 100), 2) AS fill_percentage,
    r.unit,
    r.last_updated
FROM resources r
JOIN spacecraft s ON r.spacecraft_id = s.id
JOIN resource_types rt ON r.resource_type_id = rt.id;