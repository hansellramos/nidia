CREATE SCHEMA `transporters_inc` ;

CREATE TABLE `stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `containersDispatched` double NOT NULL,
  `containersNotDispatched` double NOT NULL,
  `budgetUsed` double NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `containers` json NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE DEFINER=`root`@`localhost` PROCEDURE `getContainers`()
BEGIN
	SELECT REPLACE(REPLACE(REPLACE(GROUP_CONCAT(JSON_EXTRACT(containers, '$[*].name')),'[',''),']',''),'"','') AS containers FROM stats;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `getStats`(IN start_date Date, IN end_date Date)
BEGIN
	IF (start_date != null) AND (end_date != null) THEN
		SELECT COALESCE(SUM(containersDispatched),0), COALESCE(SUM(containersNotDispatched),0), COALESCE(SUM(budgetUsed),0) FROM stats WHERE date between start_date and end_date;
    ELSEIF ( start_date != null) THEN
		SELECT COALESCE(SUM(containersDispatched),0), COALESCE(SUM(containersNotDispatched),0), COALESCE(SUM(budgetUsed),0) FROM stats WHERE cast(date as date) = start_date;
    ELSE 
		SELECT COALESCE(SUM(containersDispatched),0) as containersDispatched, COALESCE(SUM(containersNotDispatched),0) as containersNotDispatched,COALESCE(SUM(budgetUsed),0) as  budgetUsed FROM stats;
	END IF;
END

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertStats`(IN containers_dispatched DOUBLE, IN containers_not_dispatched DOUBLE, IN budget_used DOUBLE, IN var_containers JSON)
BEGIN
	INSERT INTO stats (containersDispatched,containersNotDispatched,budgetUsed,containers)  VALUES (containers_dispatched,containers_not_dispatched,budget_used,var_containers);
END
