-- Créer un utilisateur avec mot de passe
CREATE USER user_hyban WITH PASSWORD 'marwan';

-- Donner tous les privilèges à l'utilisateur sur la base de données
GRANT ALL PRIVILEGES ON DATABASE hyban_db TO user_hyban;

-- Appliquer les privilèges sur les futurs schémas et tables
ALTER DATABASE hyban_db OWNER TO user_hyban;

-- Autoriser les connexions distantes
ALTER ROLE user_hyban SUPERUSER;
