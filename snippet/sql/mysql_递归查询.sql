WITH RECURSIVE parent_chain AS (
    SELECT *
    FROM blade_menu
    WHERE id = 1885528892580106241
  UNION ALL
    SELECT c.*
    FROM blade_menu c
    INNER JOIN parent_chain pc ON c.parent_id = pc.id
    where c.parent_id is not null
)
SELECT * FROM parent_chain;