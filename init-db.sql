-- Créer un utilisateur "postgres" si nécessaire (généralement déjà créé par défaut)
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'postgres') THEN
    CREATE USER postgres WITH PASSWORD 'postgres';
  END IF;
END
$$;

-- Créer un utilisateur "user_hyban" avec mot de passe
DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'user_hyban') THEN
    CREATE USER user_hyban WITH PASSWORD 'marwan';
  END IF;
END
$$;

-- Donner tous les privilèges à l'utilisateur "user_hyban" sur la base de données
GRANT ALL PRIVILEGES ON DATABASE hyban_db TO user_hyban;

-- Appliquer les privilèges sur les futurs schémas et tables
ALTER DATABASE hyban_db OWNER TO user_hyban;

-- Autoriser l'utilisateur "user_hyban" à être SUPERUSER
ALTER ROLE user_hyban SUPERUSER;

-- Assurer que l'utilisateur "postgres" est SUPERUSER et a accès à tout
ALTER ROLE postgres SUPERUSER;

-- Modifier les paramètres de pg_hba.conf pour autoriser les connexions distantes
UPDATE pg_settings SET setting = 'trust' WHERE name = 'authentication_timeout';

