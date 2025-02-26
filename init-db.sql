-- Créer un utilisateur "user_hyban" avec mot de passe
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'directus') THEN
    CREATE USER user_hyban WITH PASSWORD 'directus';
  END IF;
END
$$;

-- Donner tous les privilèges à l'utilisateur "user_hyban" sur la base de données
GRANT ALL PRIVILEGES ON DATABASE hyban_db TO directus;

-- Appliquer les privilèges sur les futurs schémas et tables
ALTER DATABASE hyban_db OWNER TO directus;

-- Autoriser l'utilisateur "user_hyban" à être SUPERUSER
ALTER ROLE directus SUPERUSER;

-- Assurer que l'utilisateur "postgres" est SUPERUSER et a accès à tout
ALTER ROLE postgres SUPERUSER;

-- Modifier les paramètres de pg_hba.conf pour autoriser les connexions distantes
UPDATE pg_settings SET setting = 'trust' WHERE name = 'authentication_timeout';

