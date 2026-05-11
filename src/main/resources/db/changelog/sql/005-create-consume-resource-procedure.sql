CREATE OR REPLACE PROCEDURE consume_resource(
    p_spacecraft_id INT,
    p_resource_type_name VARCHAR(50),
    p_amount DECIMAL(12,3)
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_resource_id INT;
    v_current_qty DECIMAL(12,3);
    v_new_qty DECIMAL(12,3);
BEGIN
    SELECT r.id, r.current_quantity
    INTO v_resource_id, v_current_qty
    FROM resources r
    JOIN resource_types rt ON r.resource_type_id = rt.id
    WHERE r.spacecraft_id = p_spacecraft_id AND rt.name = p_resource_type_name
    FOR UPDATE;

    IF v_current_qty IS NULL THEN
        RAISE EXCEPTION 'Ресурс типа % для аппарата ID % не найден', p_resource_type_name, p_spacecraft_id;
    END IF;

    v_new_qty := v_current_qty - p_amount;
    IF v_new_qty < 0 THEN
        RAISE EXCEPTION 'Недостаточно ресурса. Доступно: %, требуется: %', v_current_qty, p_amount;
    END IF;

    UPDATE resources SET current_quantity = v_new_qty, last_updated = NOW()
    WHERE id = v_resource_id;

    INSERT INTO resource_logs (spacecraft_id, resource_id, quantity_change, timestamp)
    VALUES (p_spacecraft_id, v_resource_id, -p_amount, NOW());
END;
$$;