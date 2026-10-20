workspace "C4_Elements" "" {
	model {
		properties {
			"structurizr.groupSeparator" "/"
		}
		personAlias = Person "A person" "This is a person"
		group "Company boundary" {
			group "Domain boundary" {
				systemAlias = softwareSystem "A system" "{
					group "Container boundary" {
						containerAlias = container "A container" {
							componentAlias = component "A component"
						}
					}
				}
			}
		}
		personAlias -> systemAlias "A relationship between person and system" "This is person to system technology"
		personAlias -> containerAlias "A relationship between person and container" "This is person to container technology"
		personAlias -> componentAlias "A relationship between person and component" "This is person to component technology"
	}
	views {
		systemContext systemAlias context_of_systemAlias "Context view of \"A system\"" {
		}
		container systemAlias containers_of_systemAlias "Container view of \"A system\"" {
		}
		component containerAlias components_of_containerAlias "Component view of \"A container\"" {
		}
	}
}