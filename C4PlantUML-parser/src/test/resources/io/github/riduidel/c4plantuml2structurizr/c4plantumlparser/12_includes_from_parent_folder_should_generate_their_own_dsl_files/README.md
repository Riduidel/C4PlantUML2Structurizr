This folder contains a weird example (similar to my business use case).
It seems like some companies define software systems in `.plantuml` diagrams
defined in a parent folder.
In such a case, it is presented in `plantuml` files as

```
...
!include ../../../../../12_includes_from_parent_folder_should_generate_their_own_dsl_files/context_level_include.puml
...
```

In such a case, we should

1. Create a workspace.dsl file for the include containing the required elements, named `${INPUT}.workspace.dsl`
2. Use Structurizr [!include](https://docs.structurizr.com/dsl/language#include-1) statement to get the content in our DSL file