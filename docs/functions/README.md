# Functions


## NLyze: Interactive Programming by Natural Language for SpreadSheet Data Analysis and Manipulation

```antlrv4
Program := MakeActive(Q) | Format(fe,Q) | v | V
QueryExprQ := SelectRows(rs,f) | SelectCells(C ̃,rs,f)
Row Source Expr rs := GetTable(Tbl) | MTable() | GetActive() | GetFormat(Tbl,fe)
Format Expr fe := {fmt1, . . . , fmtn}
Filter Expr f := relop(C, v) | relop(v, C) | relop(C, C) | And(f,f) | Or(f,f) | Not(f) | True 
Scalar Expr v := rop(C,rs,f) | Count(rs,f) | bop(v,v) | Lookup(v,rs,C,C) | c
Vector Expr V := bop(V,V ) | bop(V,v) | bop(v,V ) | C | Lookup(C, rs, C, C)
Format Fn fmt := Color(c) | FontSize (c) | Bold(b) | Italics (b) | Unserline(b) ...

Binary Fn bop := Add|Sub|Mult|Div 
Reduce Fn rop := Sum|Avg|Min|Max 
Relational Fn relop := Lt|Gt|Eq
```
