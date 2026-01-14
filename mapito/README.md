# 🗺️ Mapito

> “Don’t Panic.”

**Mapito** é uma biblioteca Java leve para converter **POJOs ⇄ Map<String,Object>**, ideal para universos caóticos, APIs imprevisíveis e dados que mudam de forma como Zaphod troca de cabeça.

Ela oferece:

* Flatten (mapa hierárquico → mapa plano)
* Builder fluente
* Merge de objetos (estilo PATCH)
* Conversão POJO ⇄ Map
* Integração com Jackson

---

## 📦 Instalação (Maven)

```xml
<dependency>
    <groupId>io.github.frankleyrocha.arcturus</groupId>
    <artifactId>mapito</artifactId>
    <version>0.0.1</version>
</dependency>
```

---

## 🧩 Exemplo de Classes

```java
public class Hitchhiker {
    public String name;
    public String species;
    public Planet planet;
}

public class Planet {
    public String name;
    public String galaxy;
}
```

---

## 🔁 Converter objeto para Map

```java
Hitchhiker zaphod = new Hitchhiker();
zaphod.name = "Zaphod Beeblebrox";
zaphod.species = "Betelgeusian";

Map<String, Object> map = Mapito.toMap(zaphod);
```

Resultado:

```json
{
  "name": "Zaphod Beeblebrox",
  "species": "Betelgeusian",
  "planet": null
}
```

---

## 🗺️ Converter para Flat Map

```java
Hitchhiker arthur = new Hitchhiker();
arthur.name = "Arthur Dent";
arthur.species = "Human";

arthur.planet = new Planet();
arthur.planet.name = "Earth";
arthur.planet.galaxy = "Milky Way";

Map<String, Object> flat = Mapito.toFlatMap(arthur);
```

Resultado:

```json
{
  "name": "Arthur Dent",
  "species": "Human",
  "planet.name": "Earth",
  "planet.galaxy": "Milky Way"
}
```

---

## 🧱 Criar objetos com Builder

```java
Hitchhiker ford = Mapito.getBuilder(Hitchhiker.class)
    .with("name", "Ford Prefect")
    .with("species", "Betelgeusian")
    .with("planet", Map.of(
        "name", "Betelgeuse Five",
        "galaxy", "Betelgeuse"
    ))
    .build();
```

---

## 🔄 Converter objeto para Builder

Útil para ajustes rápidos no multiverso:

```java
Hitchhiker updated = Mapito.toBuilder(ford)
    .with("species", "Mostly Harmless")
    .build();
```

---

## 🔀 Merge de objetos (Patch)

Somente valores **não-nulos** do `source` substituem o `target`.

```java
Hitchhiker base = new Hitchhiker();
base.name = "Trillian";
base.species = "Human";

Hitchhiker patch = new Hitchhiker();
patch.species = "Mostly Human";

Hitchhiker merged = Mapito.merge(base, patch);
```

Resultado:

```java
merged.name    == "Trillian"
merged.species == "Mostly Human"
```

---

## 🔁 Map → POJO

```java
Map<String, Object> map = new HashMap<>();
map.put("name", "Marvin");
map.put("species", "Android");

Hitchhiker marvin = Mapito.fromMap(Hitchhiker.class, map);
```

---

## 🧠 Por que usar Mapito?

No universo real (e no de Douglas Adams), dados são:

* Parciais
* Caóticos
* Mutáveis
* Cheios de campos opcionais

Mapito resolve isso permitindo:

* Atualizações estilo **PATCH**
* Transformação dinâmica de objetos
* Integração simples com APIs e JSON
* Mapeamento sem boilerplate

Tudo isso usando o **Jackson** como motor de dobra espacial.

---

## 📜 Licença

MIT — livre como um mochileiro interestelar.
