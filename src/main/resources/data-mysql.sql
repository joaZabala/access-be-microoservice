INSERT INTO setup (target_time, time_of_grace)
SELECT '18:30:00', 15
WHERE NOT EXISTS (SELECT 1 FROM setup LIMIT 1);
