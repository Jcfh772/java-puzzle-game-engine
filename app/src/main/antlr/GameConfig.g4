grammar GameConfig;

// Parser rules
config: declaration+ EOF;

declaration
    : sizeDecl
    | startDecl
    | goalDecl
    | itemDecl
    | obstacleDecl
    | pluginDecl
    | scriptDecl
    ;

sizeDecl: 'size' coord;
startDecl: 'start' coord;
goalDecl: 'goal' coord;

itemDecl: 'item' STRING '{' itemBody '}';
itemBody: atClause messageClause?;

obstacleDecl: 'obstacle' '{' obstacleBody '}';
obstacleBody: atClause requiresClause;

atClause: 'at' coordList;
coordList: coord (',' coord)*;

messageClause: 'message' STRING;
requiresClause: 'requires' stringList;
stringList: STRING (',' STRING)*;

pluginDecl: 'plugin' IDENTIFIER ('.' IDENTIFIER)*;

scriptDecl: 'script' SCRIPT;

coord: '(' INT ',' INT ')';

// Lexer rules
SCRIPT: '!{' .*? '}';
STRING: '"' (~["\r\n])* '"';
INT: [0-9]+;
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;