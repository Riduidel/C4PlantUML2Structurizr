workspace "C4_Elements" "" {
	model {
		personAlias = Person "A person" "This is a person"
		systemAlias = softwareSystem "A system" "This is a system"
		personAlias -> systemAlias "A relationship" "This is a technology"
	}
	views {
		systemContext systemAlias context_of_systemAlias "Context view of \"A system\"" {
		}
	}
}