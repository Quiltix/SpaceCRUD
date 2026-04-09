create index if not exists idx_missions_spacecraft_id
    on missions using btree (spacecraft_id);

create index if not exists idx_experiments_mission_id
    on experiments using btree (mission_id);

create index if not exists idx_experiments_responsible_member_id
    on experiments using btree (responsible_member_id);

create index if not exists idx_resources_spacecraft_id
    on resources using btree (spacecraft_id);

create index if not exists idx_resources_resource_type_id
    on resources using btree (resource_type_id);

create index if not exists idx_mission_crew_member_id
    on mission_crew using btree (member_id);

create index if not exists idx_crew_members_first_last_name
    on crew_members using btree (first_name, last_name);

create index if not exists idx_crew_members_birth_date
    on crew_members using btree (birth_date);

create index if not exists idx_resource_logs_spacecraft_timestamp_desc
    on resource_logs using btree (spacecraft_id, "timestamp" desc);

create index if not exists idx_resource_logs_resource_timestamp_desc
    on resource_logs using btree (resource_id, "timestamp" desc);
